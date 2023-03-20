package com.github.boybeak.v8x.gl.webgl

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
    }

}