package com.github.boybeak.v8x.canvas.v8

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.canvas.CanvasView
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.canvas.webgl.IWebGLRenderingContext
import com.github.boybeak.v8x.ext.registerV8Methods
import com.github.boybeak.v8x.canvas.webgl.Constants
import java.io.File

class V8WebCanvasView : CanvasView, IV8Canvas {

    companion object {
        private const val TAG = "V8WebCanvasView"
    }

    private lateinit var canvasContext: IV8WebGLRenderingContext
    private lateinit var v8inner: V8
    override val v8: V8
        get() = v8inner

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
    }

    private fun initV8Env() {
        v8inner = V8.createV8Runtime()

        val canvasV8 = V8Object(v8)
        canvasV8.registerV8Methods(this)
        v8.add("canvas", canvasV8)
        canvasV8.close()

        val console = Console()
        val consoleV8 = V8Object(v8)
        consoleV8.registerV8Methods(console)
        v8.add("console", consoleV8)
        consoleV8.close()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        queueEvent{
            initV8Env()
        }
        if (startTaskBeforeAttach != null) {
            queueEvent(startTaskBeforeAttach!!)
        }
    }

    @V8Method(jsFuncName = "getContext")
    fun getContextV8(type: String): V8Object {
        Log.d(TAG, "getContextV8 type=$type")
        val iWebGLContext = getContext(type)
        val ctxV8 = V8Object(canvasContext.v8).apply {
            Constants.getMap().forEach { (t, u) ->
                add(t, u)
            }
        }
        ctxV8.registerV8Methods(iWebGLContext)
        return ctxV8
    }

    override fun getContext(type: String): IWebGLRenderingContext {
        if (!::canvasContext.isInitialized) {
            canvasContext = when(type) {
                "webgl" -> V8WebGLRenderingContext(this)
                else -> TODO("Wait to ...")
            }
            setRenderer(canvasContext)
//            setRenderMode(ICanvasRenderer.RENDER_MODE_CONTINUOUSLY)
        }
        return canvasContext
    }

    private var startTaskBeforeAttach: Runnable? = null
    fun start(jsFile: File) {
        val task = Runnable {
            Log.d(TAG, "start -->")
            v8.executeScript(jsFile.readText())
            requestRender()
        }
        if (isAttachedToWindow) {
            queueEvent(task)
        } else {
            startTaskBeforeAttach = task
        }

    }

}