package com.github.boybeak.v8x.app.model

import android.util.Log
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.V8BindingAdapter
import com.github.boybeak.v8x.binding.annotation.V8Field

class User : V8BindingAdapter {

    companion object {
        private const val TAG = "User"
    }

    @V8Field(binding = true)
    var name: String? = null
    @V8Field(binding = true)
    var location: Location? = null

    override fun getBindingId(): String {
        return hashCode().toString()
    }

    override fun onStringBindingChanged(
        target: V8Object,
        fieldName: String,
        newValue: String,
        oldValue: String
    ) {
        Log.d(TAG, "onStringBindingChanged(fieldName=$fieldName, newValue=$newValue)")
    }

    override fun onBindingCreated(target: V8Object, fieldInfo: Key, value: V8Object): V8Binding {
        Log.d(TAG, "onBindingCreated(fieldInfo.name=${fieldInfo.name}, value=${value})")
        return when(fieldInfo.name) {
            "location" -> Location()
            else -> super.onBindingCreated(target, fieldInfo, value)
        }
    }
}