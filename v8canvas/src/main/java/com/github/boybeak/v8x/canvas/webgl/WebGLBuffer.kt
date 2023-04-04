package com.github.boybeak.v8x.canvas.webgl

import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.annotation.V8Field

class WebGLBuffer(@V8Field val bufferId: Int) : WebGLObject {
    companion object {
        fun fromV8Object(v8obj: V8Object): WebGLBuffer {
            return WebGLBuffer(v8obj.getInteger("bufferId"))
        }
    }
}