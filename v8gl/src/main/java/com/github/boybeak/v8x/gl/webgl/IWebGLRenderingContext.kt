package com.github.boybeak.v8x.gl.webgl

typealias GLclampf = Float
typealias GLbitfield = Long
typealias GLenum = Int
typealias GLboolean = Boolean
typealias GLint = Int
typealias GLsizei = Int

interface IWebGLRenderingContext {
    fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf)
    fun clear(mask: GLbitfield)

    fun createShader(type: GLenum): WebGLShader
    fun shaderSource(shader: WebGLShader, source: String)
    fun compileShader(shader: WebGLShader)
    fun getShaderParameter(shader: WebGLShader, pname: GLenum): Any

    fun createProgram(): WebGLProgram
    fun attachShader(program: WebGLProgram, shader: WebGLShader)
    fun linkProgram(program: WebGLProgram)
    fun getProgramParameter(program: WebGLProgram, pname: GLenum): Any
    fun useProgram(program: WebGLProgram)
    fun getAttribLocation(program: WebGLProgram, name: String): GLint
    fun vertexAttrib1f(index: GLint, x: Float)
    fun vertexAttrib2f(index: GLint, x: Float, y: Float)
    fun vertexAttrib3f(index: GLint, x: Float, y: Float, z: Float)
    fun vertexAttrib4f(index: GLint, x: Float, y: Float, z: Float, w: Float)

    fun vertexAttrib1fv(index: GLint, v: FloatArray)
    fun vertexAttrib2fv(index: GLint, v: FloatArray)
    fun vertexAttrib3fv(index: GLint, v: FloatArray)
    fun vertexAttrib4fv(index: GLint, v: FloatArray)
    fun drawArrays(mode: GLint, first: GLint, count: GLsizei)
}