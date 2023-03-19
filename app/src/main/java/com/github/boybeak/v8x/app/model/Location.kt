package com.github.boybeak.v8x.app.model

import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.V8BindingAdapter

class Location : V8BindingAdapter {
    override fun getBindingId(): String {
        return hashCode().toString()
    }

    override fun onDoubleBindingChanged(
        target: V8Object,
        fieldName: String,
        newValue: Double,
        oldValue: Double
    ) {
        super.onDoubleBindingChanged(target, fieldName, newValue, oldValue)
    }
}