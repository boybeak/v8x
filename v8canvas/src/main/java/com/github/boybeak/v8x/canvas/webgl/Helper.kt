package com.github.boybeak.v8x.canvas.webgl

fun getInt(block: (IntArray) -> Unit): Int {
    val array = IntArray(1)
    block.invoke(array)
    return array[0]
}