package com.github.boybeak.v8x.binding

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object

data class Key(val name: String, val typeName: String) {
    companion object {
        fun from(v8obj: V8Object): Key {
            return Key(v8obj.getString("name"), v8obj.getString("typeName"))
        }
    }
    fun toV8Object(v8: V8): V8Object {
        return V8Object(v8).apply {
            add("name", name)
            add("typeName", typeName)
        }
    }
}
