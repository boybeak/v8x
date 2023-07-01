package com.github.boybeak.v8x.ext

import com.eclipsesource.v8.V8Function

private val functionNameRegex = Regex("function\\s+[a-zA-Z_]\\w+\\s*\\(")
private val head = Regex("function\\s+")
private val tail = Regex("\\s*\\(")

val V8Function.guessName: String get() {
    return functionNameRegex.find(this.toString())?.value?.replace(head, "")?.replace(tail, "")
        ?: ""
}