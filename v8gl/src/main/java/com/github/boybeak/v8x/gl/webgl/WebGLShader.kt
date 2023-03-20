package com.github.boybeak.v8x.gl.webgl

import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.annotation.V8Field

data class WebGLShader(@V8Field val shaderId: Int) : WebGLObject {
    companion object {
        fun from(v8obj: V8Object): WebGLShader {
            return WebGLShader(v8obj.getInteger("shaderId"))
        }
    }
}