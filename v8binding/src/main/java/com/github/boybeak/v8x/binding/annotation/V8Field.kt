package com.github.boybeak.v8x.binding.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class V8Field(val binding: Boolean = false)
