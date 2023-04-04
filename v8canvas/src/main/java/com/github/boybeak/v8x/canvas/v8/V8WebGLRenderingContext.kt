package com.github.boybeak.v8x.canvas.v8

import android.opengl.GLES20
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8TypedArray
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.canvas.webgl.*
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import kotlin.random.Random

class V8WebGLRenderingContext(val iCanvas: IV8Canvas) : WebGLRenderingContext(), IV8WebGLRenderingContext {

    companion object {
        private const val TAG = "V8WebGLRenderingContext"
    }

    override val v8: V8 get() = iCanvas.v8

    private val random = Random(System.currentTimeMillis())

    override fun onSurfaceCreated(gl10: EGL10, config: EGLConfig) {
        super.onSurfaceCreated(gl10, config)
        Log.d(TAG, "onSurfaceCreated thread=${Thread.currentThread().name}")
    }

    override fun onSurfaceChanged(gl10: EGL10, width: Int, height: Int) {
        super.onSurfaceChanged(gl10, width, height)
        Log.d(TAG, "onSurfaceChanged thread=${Thread.currentThread().name}")
    }
    override fun onDrawFrame(gl10: EGL10) {
        super.onDrawFrame(gl10)
        Log.d(TAG, "onDrawFrame")
//        (iCanvas as V8WebCanvasView).requestRender()
    }

    @V8Method
    override fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf) {
        super.clearColor(red, green, blue, alpha)
    }

    @V8Method
    override fun clear(mask: GLbitfield) {
        super.clear(mask)
    }

    @V8Method(jsFuncName = "createBuffer")
    fun v8CreateBuffer(): V8Object {
        return createBuffer().toV8Object(v8)
    }

    @V8Method(jsFuncName = "bindBuffer")
    fun v8BindBuffer(target: GLenum, buffer: V8Object) {
        bindBuffer(target, WebGLBuffer.fromV8Object(buffer))
    }

    @V8Method(jsFuncName = "bufferData")
    fun v8BufferData(vararg args: Any) {
        val target = args[0] as GLenum
        when(args.size) {
            2 -> {
                val usage = args[1] as GLenum
                bufferData(target, usage)
            }
            3 -> {
                val usage = args[2] as GLenum
                when(args[1]) {
                    is V8TypedArray -> {
                        val srcData = args[1] as V8TypedArray
                        bufferData(target, srcData.byteBuffer, usage)
                    }
                }
            }
        }
    }

    @V8Method
    override fun vertexAttribPointer(
        index: GLuint,
        size: GLint,
        type: GLenum,
        normalized: GLboolean,
        stride: GLsizei,
        offset: GLintptr
    ) {
        super.vertexAttribPointer(index, size, type, normalized, stride, offset)
    }

    @V8Method
    override fun enableVertexAttribArray(index: GLuint) {
        super.enableVertexAttribArray(index)
    }

    @V8Method(jsFuncName = "createShader")
    fun v8CreateShader(type: GLenum): V8Object {
        return createShader(type).toV8Object(v8)
    }

    @V8Method(jsFuncName = "shaderSource")
    fun v8ShaderSource(shader: V8Object, source: String) {
        shaderSource(WebGLShader.from(shader), source)
    }

    @V8Method(jsFuncName = "compileShader")
    fun v8CompileShader(shader: V8Object) {
        compileShader(WebGLShader.from(shader))
    }

    @V8Method(jsFuncName = "getShaderParameter")
    fun xv8GetShaderParameter(shader: V8Object, pname: GLenum): Any {
        return getShaderParameter(WebGLShader.from(shader), pname)
    }

    @V8Method(jsFuncName = "createProgram")
    fun v8CreateProgram(): V8Object {
        return createProgram().toV8Object(v8)
    }

    @V8Method(jsFuncName = "attachShader")
    fun v8AttachShader(program: V8Object, shader: V8Object) {
        attachShader(WebGLProgram.from(program), WebGLShader.from(shader))
    }

    @V8Method(jsFuncName = "linkProgram")
    fun v8LinkProgram(program: V8Object) {
        linkProgram(WebGLProgram.from(program))
    }

    @V8Method(jsFuncName = "getProgramParameter")
    fun v8GetProgramParameter(program: V8Object, pname: GLenum): Any {
        return getProgramParameter(WebGLProgram.from(program), pname)
    }

    @V8Method(jsFuncName = "useProgram")
    fun v8UseProgram(program: V8Object) {
        Log.d(TAG, "v8UseProgram -->")
        useProgram(WebGLProgram.from(program))
    }

    @V8Method(jsFuncName = "getAttribLocation")
    fun v8GetAttribLocation(program: V8Object, name: String): GLint {
        return getAttribLocation(WebGLProgram.from(program), name)
    }

    @V8Method
    override fun vertexAttrib1f(index: GLint, x: Float) {
        super.vertexAttrib1f(index, x)
    }

    @V8Method
    override fun vertexAttrib2f(index: GLint, x: Float, y: Float) {
        super.vertexAttrib2f(index, x, y)
    }

    @V8Method
    override fun vertexAttrib3f(index: GLint, x: Float, y: Float, z: Float) {
        super.vertexAttrib3f(index, x, y, z)
    }

    @V8Method
    override fun vertexAttrib4f(index: GLint, x: Float, y: Float, z: Float, w: Float) {
        super.vertexAttrib4f(index, x, y, z, w)
    }

    @V8Method
    override fun drawArrays(mode: GLint, first: GLint, count: GLsizei) {
        super.drawArrays(mode, first, count)
        Log.d(TAG, "drawArrays -->")
    }
}