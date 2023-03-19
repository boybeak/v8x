package com.github.boybeak.v8x.gl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.registerV8Methods
import com.github.boybeak.v8x.gl.webgl.ICanvas
import com.github.boybeak.v8x.gl.webgl.IWebGLRenderingContext
import java.io.File

class V8GLView : GLSurfaceView, ICanvas {

    companion object {
        private const val TAG = "V8GLView"
    }

    private lateinit var v8glRenderer: V8GLRenderer

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setEGLContextClientVersion(2)
    }

    fun start(jsFile: File) {
        v8glRenderer = V8GLRenderer(jsFile, this)
        setRenderer(v8glRenderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    override fun getContext(type: String): IWebGLRenderingContext {
        return v8glRenderer
    }

    @V8Method(jsFuncName = "getContext")
    fun getContextV8(type: String): V8Object {
        Log.d(TAG, "getContextV8 type=$type")
        val iWebGLContext = getContext(type)
        val ctxV8 = V8Object(v8glRenderer.v8).apply {
            add("COLOR_BUFFER_BIT", 0x00004000)
        }
        ctxV8.registerV8Methods(iWebGLContext)
        return ctxV8
    }

}