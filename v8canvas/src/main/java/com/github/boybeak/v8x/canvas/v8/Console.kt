package com.github.boybeak.v8x.canvas.v8

import android.util.Log
import com.github.boybeak.v8x.binding.annotation.V8Method

class Console {

    companion object  {
        private const val TAG = "Console"
    }

    @V8Method
    fun log(vararg args: Any) {
        Log.i(TAG, args.joinToString(separator = ""))
    }
}