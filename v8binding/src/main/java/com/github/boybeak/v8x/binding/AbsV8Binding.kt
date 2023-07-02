package com.github.boybeak.v8x.binding

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object

abstract class AbsV8Binding : V8Binding {

    private var v8Inner: V8? = null
    val v8: V8 get() {
        if (v8Inner == null) {
            throw IllegalStateException("You must call getMyBinding before use v8 field")
        }
        return v8Inner!!
    }
    override fun getMyBinding(v8: V8): V8Object {
        if (v8Inner == null) {
            this.v8Inner = v8
        }
        return super.getMyBinding(v8)
    }

}