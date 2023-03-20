package com.github.boybeak.v8x.gl

import android.opengl.GLSurfaceView.Renderer
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.registerV8Methods
import com.github.boybeak.v8x.gl.webgl.*
import java.io.File
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class V8GLRenderer(private val jsFile: File, private val v8GLView: V8GLView) : WebGLRenderingContext(v8GLView), Renderer {

    val v8: V8 by lazy {
        V8.createV8Runtime("", jsFile.parent)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val canvasV8 = V8Object(v8)
        canvasV8.registerV8Methods(iCanvas)
        v8.add("canvas", canvasV8)
        v8.executeScript(jsFile.readText())
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    }

    override fun onDrawFrame(gl: GL10?) {
    }

    @V8Method
    override fun clearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf) {
        super.clearColor(red, green, blue, alpha)
    }

    @V8Method
    override fun clear(mask: GLbitfield) {
        super.clear(mask)
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
    fun v8GetShaderParameter(shader: V8Object, pname: GLenum): Any {
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

}