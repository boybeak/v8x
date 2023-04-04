package com.github.boybeak.v8x.canvas.webgl

import android.opengl.GLES20

interface Constants {
    companion object {
        fun getMap(): Map<String, Int> {
            val map = HashMap<String, Int>()
            for (field in Constants::class.java.declaredFields) {
                if (field.type != Int::class.java) {
                    continue
                }
                map[field.name] = field.getInt(null)
            }
            return map
        }
        const val COLOR_BUFFER_BIT = GLES20.GL_COLOR_BUFFER_BIT
        const val VERTEX_SHADER = GLES20.GL_VERTEX_SHADER
        const val FRAGMENT_SHADER = GLES20.GL_FRAGMENT_SHADER
        const val TRUE = GLES20.GL_TRUE
        const val FALSE = GLES20.GL_FALSE
        const val DELETE_STATUS = GLES20.GL_DELETE_STATUS
        const val COMPILE_STATUS = GLES20.GL_COMPILE_STATUS
        const val LINK_STATUS = GLES20.GL_LINK_STATUS
        const val VALIDATE_STATUS = GLES20.GL_VALIDATE_STATUS
        const val SHADER_TYPE = GLES20.GL_SHADER_TYPE
        const val ATTACHED_SHADERS = GLES20.GL_ATTACHED_SHADERS
        const val ACTIVE_ATTRIBUTES = GLES20.GL_ACTIVE_ATTRIBUTES
        const val ACTIVE_UNIFORMS = GLES20.GL_ACTIVE_UNIFORMS
        const val POINTS = GLES20.GL_POINTS
        const val TRIANGLES = GLES20.GL_TRIANGLES
        const val ARRAY_BUFFER = GLES20.GL_ARRAY_BUFFER
        const val STATIC_DRAW = GLES20.GL_STATIC_DRAW
        const val FLOAT = GLES20.GL_FLOAT
    }

}