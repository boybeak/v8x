package com.github.boybeak.v8x.binding.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class V8Field(val binding: Boolean = false)
