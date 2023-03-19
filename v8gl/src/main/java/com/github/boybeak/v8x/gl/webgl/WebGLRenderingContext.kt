package com.github.boybeak.v8x.gl.webgl

import android.opengl.GLES20

open class WebGLRenderingContext(val iCanvas: ICanvas) : IWebGLRenderingContext {
    override fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf) {
        GLES20.glClearColor(red, green, blue, alpha)
    }

    override fun clear(mask: GLbitfield) {
        GLES20.glClear(mask.toInt())
    }
}