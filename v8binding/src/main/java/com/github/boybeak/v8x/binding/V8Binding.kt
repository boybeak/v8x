package com.github.boybeak.v8x.binding

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object

interface V8Binding {
    companion object {
        private const val TAG = "V8Binding"
    }
    fun getBindingId(): String
    fun getMyBinding(v8: V8): V8Object {
        return V8Manager.obtain(v8).run {
            if (!isBound(getBindingId())) {
                createBinding(this@V8Binding)
            } else {
                getBinding(getBindingId())
            }
        }
    }

    /**
     * Use this if some properties can not be declared as a native field
     */
    fun getAdditionalPropertyNames(): Array<String>? {
        return null
    }
    fun onAdditionalPropertyChanged(target: V8Object, propertyName: String, newValue: Any?, oldValue: Any?) {
        throw NotImplementedError("onAdditionalPropertyChanged must be implemented if getAdditionalPropertyNames returned nut null nor empty")
    }
    fun onBindingCreated(target: V8Object, fieldInfo: Key, value: V8Object): V8Binding {
        throw NotImplementedError("onCreateBinding must be implemented when new binding created")
    }
    fun onBindingDestroyed(target: V8Object, fieldInfo: Key) {
        throw NotImplementedError("onBindingDestroyed must be implemented when binding destroyed")
    }
    fun onBindingChanged(target: V8Object, fieldInfo: Key, newValue: Any?, oldValue: Any?) {}
}