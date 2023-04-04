package com.github.boybeak.v8x.canvas.v8

import com.eclipsesource.v8.V8
import com.github.boybeak.v8x.canvas.ICanvas

interface IV8Canvas : ICanvas {
    val v8: V8
}