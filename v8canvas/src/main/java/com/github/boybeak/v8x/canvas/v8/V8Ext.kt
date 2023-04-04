package com.github.boybeak.v8x.canvas.v8

import android.util.Log
import com.eclipsesource.v8.V8TypedArray
import com.eclipsesource.v8.V8Value
import java.nio.ByteBuffer

private const val TAG = "V8Ext"

val V8TypedArray.bytes: ByteArray get() {
    val bytes = ByteArray(this.sizeInByte)
    this.buffer.get(bytes)
    return bytes
}
val V8TypedArray.byteBuffer: ByteBuffer get() {
    return ByteBuffer.wrap(bytes)
}
val V8TypedArray.sizeInByte: Int get() = length() * unitBytes
val V8TypedArray.unitBytes: Int get() {
    return when(type) {
        V8Value.FLOAT_32_ARRAY -> 4
        else -> throw IllegalArgumentException("The type(${type}) is not support yet.")
    }
}