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
            else -> throw IllegalArgumentException("Unsupported pname=$pname for getShaderParameter")
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

    override fun getProgramParameter(program: WebGLProgram, pname: GLenum): Any {
        val result = getInt {
            GLES20.glGetProgramiv(program.programId, pname, it, 0)
        }
        return when(pname) {
            Constants.DELETE_STATUS, Constants.LINK_STATUS, Constants.VALIDATE_STATUS -> result == Constants.TRUE
            Constants.ATTACHED_SHADERS, Constants.ACTIVE_ATTRIBUTES, Constants.ACTIVE_UNIFORMS -> result
            else -> throw IllegalArgumentException("Unsupported pname=$pname for getProgramParameter")
        }
    }

    override fun useProgram(program: WebGLProgram) {
        GLES20.glUseProgram(program.programId)
    }

    override fun getAttribLocation(program: WebGLProgram, name: String): GLint {
        return GLES20.glGetAttribLocation(program.programId, name)
    }

    override fun vertexAttrib1f(index: GLint, x: Float) {
        GLES20.glVertexAttrib1f(index, x)
    }

    override fun vertexAttrib2f(index: GLint, x: Float, y: Float) {
        GLES20.glVertexAttrib2f(index, x, y)
    }

    override fun vertexAttrib3f(index: GLint, x: Float, y: Float, z: Float) {
        GLES20.glVertexAttrib3f(index, x, y, z)
    }

    override fun vertexAttrib4f(index: GLint, x: Float, y: Float, z: Float, w: Float) {
        GLES20.glVertexAttrib4f(index, x, y, z, w)
    }

    override fun vertexAttrib1fv(index: GLint, v: FloatArray) {
        GLES20.glVertexAttrib1fv(index, v, 0)
    }

    override fun vertexAttrib2fv(index: GLint, v: FloatArray) {
        GLES20.glVertexAttrib2fv(index, v, 0)
    }

    override fun vertexAttrib3fv(index: GLint, v: FloatArray) {
        GLES20.glVertexAttrib3fv(index, v, 0)
    }

    override fun vertexAttrib4fv(index: GLint, v: FloatArray) {
        GLES20.glVertexAttrib4fv(index, v, 0)
    }

    override fun drawArrays(mode: GLint, first: GLint, count: GLsizei) {
        GLES20.glDrawArrays(mode, first, count)
    }

}