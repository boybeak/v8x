package com.github.boybeak.v8x.canvas.webgl

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.ext.registerV8Fields

interface WebGLObject {
    fun toV8Object(v8: V8): V8Object {
        return V8Object(v8).apply {
            registerV8Fields(this@WebGLObject)
        }
    }
}