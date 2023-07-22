package com.github.boybeak.v8x.binding.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class V8Method(val jsFuncName: String = "")
