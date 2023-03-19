package com.github.boybeak.v8x.gl.webgl

interface ICanvas {
    fun getContext(type: String): IWebGLRenderingContext
}