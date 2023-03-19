package com.github.boybeak.v8x.binding

import com.eclipsesource.v8.V8Object

interface V8BindingAdapter : V8Binding {

    @Suppress("UNCHECKED_CAST")
    private fun <T> Any?.castTo(defaultValue: T): T {
        return if (this == null) {
            defaultValue
        } else {
            this as T
        }
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {
        when(fieldInfo.typeName) {
            Boolean::class.java.name -> {
                onBooleanBindingChanged(target, fieldInfo.name,
                    newValue.castTo(false), oldValue.castTo(false))
            }
            Int::class.java.name -> {
                onIntBindingChanged(target, fieldInfo.name,
                    newValue.castTo(0), oldValue.castTo(0))
            }
            Double::class.java.name -> {
                onDoubleBindingChanged(target, fieldInfo.name,
                    newValue.castTo(0.0), oldValue.castTo(0.0))
            }
            String::class.java.name -> {
                onStringBindingChanged(target, fieldInfo.name,
                    newValue.castTo(""), oldValue.castTo(""))}
            else -> {
                onObjectBindingChanged(target, fieldInfo.name, newValue as V8Object, oldValue as V8Object)
            }
        }
    }
    fun onBooleanBindingChanged(target: V8Object, fieldName: String, newValue: Boolean, oldValue: Boolean) {}
    fun onIntBindingChanged(target: V8Object, fieldName: String, newValue: Int, oldValue: Int) {}
    fun onDoubleBindingChanged(target: V8Object, fieldName: String, newValue: Double, oldValue: Double) {}
    fun onStringBindingChanged(target: V8Object, fieldName: String, newValue: String, oldValue: String) {}
    fun onObjectBindingChanged(target: V8Object, fieldName: String, newValue: V8Object, oldValue: V8Object) {}
}