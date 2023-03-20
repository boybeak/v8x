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

    override fun getShaderParameter(shader: WebGLShader, pname: GLenum): Any {
        val result = getInt {
            GLES20.glGetShaderiv(shader.shaderId, pname, it, 0)
        }
        return when(pname) {
            Constants.DELETE_STATUS, Constants.COMPILE_STATUS -> result == Constants.TRUE
            Constants.SHADER_TYPE -> result
            else -> { throw IllegalArgumentException("Unsupported pname=$pname") }
        }
    }

    override fun createProgram(): WebGLProgram {
        return WebGLProgram(GLES20.glCreateProgram())
    }

    override fun attachShader(program: WebGLProgram, shader: WebGLShader) {
        GLES20.glAttachShader(program.programId, shader.shaderId)
    }

    override fun linkProgram(program: WebGLProgram) {
        GLES20.glLinkProgram(program.programId)
    }

}