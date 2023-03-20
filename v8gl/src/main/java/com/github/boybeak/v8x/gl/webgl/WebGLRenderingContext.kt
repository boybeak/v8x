package com.github.boybeak.v8x.gl.webgl

import android.opengl.GLES20

open class WebGLRenderingContext(val iCanvas: ICanvas) : IWebGLRenderingContext {
    override fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf) {
        GLES20.glClearColor(red, green, blue, alpha)
    }

    override fun clear(mask: GLbitfield) {
        GLES20.glClear(mask.toInt())
    }

    override fun createShader(type: GLenum): WebGLShader {
        return WebGLShader(GLES20.glCreateShader(type))
    }

    override fun shaderSource(shader: WebGLShader, source: String) {
        GLES20.glShaderSource(shader.shaderId, source)
    }

    override fun compileShader(shader: WebGLShader) {
        GLES20.glCompileShader(shader.shaderId)
    }
}