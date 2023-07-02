package com.github.boybeak.v8x.binding

import android.util.Log
import com.eclipsesource.v8.*
import com.eclipsesource.v8.utils.V8Runnable
import com.github.boybeak.v8x.binding.annotation.V8Method
import java.util.WeakHashMap

class V8Manager private constructor(private val v8: V8){
    companion object {
        private const val TAG = "V8Manager"
        private const val FUNC_CREATE_JS_PROXY = "v8CreateProxy"
        private const val KEY_BINDING_ID = "bindingId"
        internal const val KEY_NAME = "name"
        internal const val KEY_BINDING_FIELD_MAP = "_bindingFieldMap"
        internal const val KEY_ADDITIONAL_PROPERTIES = "_additionalProperties"
        internal const val KEY_IS_V8BINDING_TYPE = "isV8BindingType"
        internal const val KEY_TYPE_NAME = "typeName"
        private const val KEY_IS_JS_PROXY_OBJ = "isJsProxyObj"

        private const val bindingMapJS = """
            const v8BindingMap = new Map();
            function setBinding(bindingId, v8obj) {
                if (hasBinding(bindingId)) {
                    if (getBinding(bindingId) == v8obj) {
                        return
                    }
                    throwError('Already bound for bindingId ' + bindingId);
                }
                v8BindingMap.set(bindingId, v8obj);
            }
            function removeBinding(bindingId) {
                if(!hasBinding(bindingId)) {
                    return;
                }
                v8BindingMap.delete(bindingId);
            }
            function hasBinding(bindingId) {
                return v8BindingMap.has(bindingId);
            }
            function getBinding(bindingId) {
                return v8BindingMap.get(bindingId);
            }
        """
        private const val createProxyJS = """
            function $FUNC_CREATE_JS_PROXY(obj) {
                return new Proxy(obj, {
                    set: function (target, key, value) {
                        if (target.$KEY_BINDING_FIELD_MAP.has(key)) {
                            console.log(key, ' In Map');
                            let oldValue = target[key];
                            if (oldValue == undefined) {
                                oldValue = null;
                            }
                            target[key] = value;
                            let newValue = value;
                            if (value == undefined) {
                                newValue = null;
                            }
                            let fieldInfo = target.$KEY_BINDING_FIELD_MAP.get(key);
                            if (fieldInfo.$KEY_IS_V8BINDING_TYPE) {
                                // if is V8Binding type
                                dispatchV8BindingChange(target, fieldInfo, newValue, oldValue);
                            } else {
                                // other types
                                dispatchBasicTypeChange(target, fieldInfo, newValue, oldValue);
                            }
                        } else {
                            let oldValue = target[key];
                            if (oldValue == undefined) {
                                oldValue = null;
                            }
                            target[key] = value;
                            let newValue = value;
                            if (value == undefined) {
                                newValue = null;
                            }
                            if (target.$KEY_ADDITIONAL_PROPERTIES != undefined && target.$KEY_ADDITIONAL_PROPERTIES.includes(key)) {
                                dispatchAdditionalProperty(target, key, value, newValue, oldValue);
                            }
                        }
                        return true;
                    }
                })
            }
        """
        private val v8ManMap = WeakHashMap<V8, V8Manager>()
        fun obtain(v8: V8): V8Manager {
            if (v8.isReleased) {
                throw IllegalStateException("The v8 runtime is already released.")
            }
            return if (v8ManMap.containsKey(v8)) {
                v8ManMap[v8]!!
            } else {
                V8Manager(v8).also {
                    v8ManMap[v8] = it
                }
            }
        }
        private fun release(v8: V8) {
            Log.d(TAG, "release")
            v8ManMap.remove(v8)?.release()
        }
    }

    private val referenceHandler = object : ReferenceHandler {

        private val V8Value?.info: String get() {
            return if (this == null) {
                "Null"
            } else if (this is V8Object) {
                "V8Object(size=${this.keys.size})[${keys.joinToString {
                    "$it:${get(it)}"
                }}]"
            } else {
                "V8Value[${this::class.java.name}]"
            }
        }

        override fun v8HandleCreated(p0: V8Value?) {
            Log.d(TAG, "v8HandleCreated thread=${Thread.currentThread().name}")
//            Log.d(TAG, "v8HandleCreated(obj=${p0.info})")
        }

        override fun v8HandleDisposed(p0: V8Value?) {
//            Log.d(TAG, "v8HandleDisposed(obj=${p0.info})")
        }
    }
    private val releaseHandler = V8Runnable {
        release(it)
    }

    private val bindingMapNative = HashMap<String, V8Binding>()

    init {
        v8.addReferenceHandler(referenceHandler)
        v8.addReleaseHandler(releaseHandler)
        v8.executeScript(bindingMapJS)
        v8.executeScript(createProxyJS)
        v8.registerJavaMethod(this, "dispatchBasicTypeChange", "dispatchBasicTypeChange",
            arrayOf(V8Object::class.java, V8Object::class.java, Any::class.java, Any::class.java))
        v8.registerJavaMethod(this, "dispatchV8BindingChange", "dispatchV8BindingChange",
            arrayOf(V8Object::class.java, V8Object::class.java, V8Object::class.java, V8Object::class.java))
        v8.registerJavaMethod(this, "dispatchAdditionalProperty", "dispatchAdditionalProperty",
            arrayOf(V8Object::class.java, String::class.java, Any::class.java, Any::class.java))
        v8.registerJavaMethod(this, "throwError", "throwError", arrayOf(String::class.java))
        if (BuildConfig.DEBUG) {
            val console = Console()
            val consoleV8 = V8Object(v8)
            V8Helper.registerV8Methods(consoleV8, console)
            v8.add("console", consoleV8)
        }
    }

    fun throwError(error: String) {
        throw V8Exception(error)
    }
    fun getBinding(bindingId: String): V8Object {
        if (!isBound(bindingId)) {
            throwError("Not bound for $bindingId")
        }
        return v8.executeObjectFunction("getBinding", V8Array(v8).apply { push(bindingId) })
    }
    fun isBound(bindingId: String): Boolean {
        return v8.executeBooleanFunction("hasBinding", V8Array(v8).apply { push(bindingId) })
    }

    internal fun createBinding(obj: V8Binding): V8Object {
        val v8obj = V8Object(v8)
        v8obj.add(KEY_BINDING_ID, obj.getBindingId())

        V8Helper.registerV8Methods(v8obj, obj)
        V8Helper.registerV8Fields(v8obj, obj)

        val v8objProxy = createJSProxy(v8obj)
        v8obj.close()
        v8.executeFunction("setBinding", V8Array(v8).apply {
            push(obj.getBindingId())
            push(v8objProxy)
        })
        bindingMapNative[obj.getBindingId()] = obj
        v8objProxy.add(KEY_IS_JS_PROXY_OBJ, true)
        return v8objProxy
    }

    private fun removeBinding(bindingId: String) {
        bindingMapNative.remove(bindingId)
        v8.executeFunction("removeBinding", V8Array(v8).apply { push(bindingId) })
    }

    private fun createJSProxy(v8obj: V8Object): V8Object {
        return v8.executeObjectFunction(FUNC_CREATE_JS_PROXY, V8Array(v8).apply { push(v8obj) })
    }
    fun dispatchBasicTypeChange(target: V8Object, fieldInfo: V8Object, newValue: Any?, oldValue: Any?) {
        val bindingId = target.getString(KEY_BINDING_ID)
        val obj = bindingMapNative[bindingId] ?: throw IllegalStateException("Illegal state, binding table not sync")
        obj.onBindingChanged(target, Key.from(fieldInfo), newValue, oldValue)
    }
    fun dispatchV8BindingChange(target: V8Object, fieldInfo: V8Object, newValue: V8Object?, oldValue: V8Object?) {
        Log.d(TAG, "dispatchBindingChange key=$fieldInfo newValue=${newValue}")

        val bindingId = target.getString(KEY_BINDING_ID)
        val obj = bindingMapNative[bindingId] ?: throw IllegalStateException("Illegal state, binding table not sync")

        if (newValue == null) {
            val fieldBindingId = (oldValue as V8Object).getString(KEY_BINDING_ID)
            removeBinding(fieldBindingId)
            obj.onBindingDestroyed(target, Key.from(fieldInfo))
        } else if (oldValue == null) {
            val v8Binding = obj.onBindingCreated(target, Key.from(fieldInfo), newValue)
            createBinding(v8Binding)
        } else {
            val fieldBindingId = oldValue.getString(KEY_BINDING_ID)
            val fieldBindingObj = bindingMapNative[fieldBindingId] ?: throw IllegalStateException("Illegal state, binding table not sync")
            // unbind first
            removeBinding(fieldBindingId)
            // rebind second
            createBinding(fieldBindingObj)

            obj.onBindingChanged(target, Key.from(fieldInfo), newValue, oldValue)
        }
    }

    fun dispatchAdditionalProperty(target: V8Object, propertyName: String, newValue: Any?, oldValue: Any?) {
        val bindingId = target.getString(KEY_BINDING_ID)
        val obj = bindingMapNative[bindingId] ?: throw IllegalStateException("Illegal state, binding table not sync")
        obj.onAdditionalPropertyChanged(target, propertyName, newValue, oldValue)
    }

    private fun release() {
        bindingMapNative.clear()
    }

    class V8Exception(message: String) : Exception(message)

    private class Console {
        companion object {
            private const val TAG = "Console"
        }
        @V8Method
        fun log(vararg args: Any) {
            Log.v(TAG, args.joinToString(separator = ""))
        }
        @V8Method
        fun error(vararg args: Any) {
            Log.e(TAG, args.joinToString(separator = ""))
        }
    }

}