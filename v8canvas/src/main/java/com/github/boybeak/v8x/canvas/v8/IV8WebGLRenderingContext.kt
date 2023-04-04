package com.github.boybeak.v8x.canvas.v8

import com.eclipsesource.v8.V8
import com.github.boybeak.v8x.canvas.webgl.IWebGLRenderingContext
import java.io.File

interface IV8WebGLRenderingContext : IWebGLRenderingContext {
    val v8: V8
}