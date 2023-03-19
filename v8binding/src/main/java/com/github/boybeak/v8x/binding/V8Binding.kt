package com.github.boybeak.v8x.binding

import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object

interface V8Binding {
    companion object {
        private const val TAG = "V8Binding"
    }
    fun getBindingId(): String
    fun getMyBinding(v8: V8): V8Object {
        Log.d(TAG, "getMyBinding")
        return V8Manager.obtain(v8).run {
            if (!isBound(getBindingId())) {
                Log.d(TAG, "getMyBinding not bound")
                createBinding(this@V8Binding)
            } else {
                Log.d(TAG, "getMyBinding bound")
                getBinding(getBindingId())
            }
        }
    }
    fun onBindingCreated(target: V8Object, fieldInfo: Key, value: V8Object): V8Binding {
        throw NotImplementedError("onCreateBinding must be implement when new binding created")
    }
    fun onBindingDestroyed(target: V8Object, fieldInfo: Key) {
        throw NotImplementedError("onBindingDestroyed must be implement when binding destroyed")
    }
    fun onBindingChanged(target: V8Object, fieldInfo: Key, newValue: Any?, oldValue: Any?)
}