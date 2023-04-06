package com.github.boybeak.v8x.binding.ext

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.V8Helper
import com.github.boybeak.v8x.binding.V8Manager

val V8.manager: V8Manager get() = V8Manager.obtain(this)

fun V8Object.registerV8Fields(obj: Any) {
    V8Helper.registerV8Fields(this, obj)
}
fun V8Object.registerV8Methods(obj: Any) {
    V8Helper.registerV8Methods(this, obj)
}