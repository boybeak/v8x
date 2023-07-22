package com.github.boybeak.v8x.binding

import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.annotation.V8Field
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.newMap
import com.github.boybeak.v8x.ext.set
import java.lang.reflect.Modifier

object V8Helper {
    private const val TAG = "V8Helper"
    fun registerV8Methods(v8obj: V8Object, otherObj: Any) {
        for (method in otherObj::class.java.methods) {
            if (!method.isAnnotationPresent(V8Method::class.java)) {
                continue
            }
            val v8Method = method.getAnnotation(V8Method::class.java) ?: continue
            if (!Modifier.isPublic(method.modifiers)) {
                throw IllegalStateException("V8Method must be public")
            }
            val jsFuncName = v8Method.jsFuncName.ifEmpty { method.name }
            v8obj.registerJavaMethod(otherObj, method.name, jsFuncName, method.parameterTypes)
        }
    }
    fun registerV8Fields(v8obj: V8Object, obj: Any) {
        val bindingMap = v8obj.runtime.newMap() // Create a js map

        for (field in obj::class.java.fields) {
            if (!field.isAnnotationPresent(V8Field::class.java)) {
                continue
            }
            val v8Field = field.getAnnotation(V8Field::class.java) ?: continue
            if (!isAcceptableV8Type(field.type)) {
                throw IllegalStateException("V8Field must mark to int, boolean, double, String or V8Binding")
            }
            field.isAccessible = true
            val name = field.name
            when(field.type) {
                Int::class.java -> v8obj.add(name, field.getInt(obj))
                Boolean::class.java -> v8obj.add(name, field.getBoolean(obj))
                Double::class.java -> v8obj.add(name, field.getDouble(obj))
                String::class.java -> v8obj.add(name, field.get(obj) as? String)
                else -> v8obj.add(name, (field.get(obj) as? V8Binding)?.getMyBinding(v8obj.runtime))
            }
            if (v8Field.binding) {
                val item = V8Object(v8obj.runtime).apply {
                    add(V8Manager.KEY_NAME, name)
                    add(V8Manager.KEY_IS_V8BINDING_TYPE, V8Binding::class.java.isAssignableFrom(field.type))
                    add(V8Manager.KEY_TYPE_NAME, field.type.name)
                }
                bindingMap.set(name, item)
                item.close()
            }
            field.isAccessible = false
        }
        v8obj.add(V8Manager.KEY_BINDING_FIELD_MAP, bindingMap)
        if (obj is V8Binding) {
            val additionalPropertyNames = obj.getAdditionalPropertyNames()
            if (!additionalPropertyNames.isNullOrEmpty()) {
                val additionalV8 = V8Array(v8obj.runtime)
                for (name in additionalPropertyNames) {
                    additionalV8.push(name)
                }
                v8obj.add(V8Manager.KEY_ADDITIONAL_PROPERTIES, additionalV8)
                additionalV8.close()
            }
        }
        bindingMap.close()
    }
    fun isAcceptableV8Type(type: Class<*>): Boolean {
        return Int::class.java == type || Boolean::class.java == type || Double::class.java == type
                || String::class.java == type || V8Binding::class.java.isAssignableFrom(type)
    }
}