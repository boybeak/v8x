package com.github.boybeak.v8x.binding.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class V8Method(val jsFuncName: String = "")
