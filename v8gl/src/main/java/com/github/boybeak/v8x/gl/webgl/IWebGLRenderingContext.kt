package com.github.boybeak.v8x.gl.webgl

typealias GLclampf = Float
typealias GLbitfield = Long

interface IWebGLRenderingContext {
    fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf)
    fun clear(mask: GLbitfield)
}