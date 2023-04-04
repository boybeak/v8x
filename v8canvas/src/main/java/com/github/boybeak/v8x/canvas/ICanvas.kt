package com.github.boybeak.v8x.canvas

import com.github.boybeak.v8x.canvas.webgl.IWebGLRenderingContext

interface ICanvas {
    fun getContext(type: String): IWebGLRenderingContext
}