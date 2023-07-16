package com.github.boybeak.v8x.ext

import com.eclipsesource.v8.V8Array

fun V8Array.getDoubleArray(): DoubleArray {
    return getDoubles(0, length())
}
fun V8Array.getFloatArray(): FloatArray {
    return getDoubleArray().let { doubles ->
        FloatArray(doubles.size) {
            doubles[it].toFloat()
        }
    }
}
fun V8Array.getInts(): IntArray {
    return getIntegers(0, length())
}