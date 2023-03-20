package com.github.boybeak.v8x.gl.webgl

typealias GLclampf = Float
typealias GLbitfield = Long
typealias GLenum = Int
typealias GLboolean = Boolean

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
}