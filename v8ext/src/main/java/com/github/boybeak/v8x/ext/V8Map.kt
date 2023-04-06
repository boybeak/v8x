package com.github.boybeak.v8x.ext

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8Value

private const val JS_FUNC_NAME_IS_MAP = "v8Ext_isMap"
private const val JS_FUNC_IS_MAP = """
    function $JS_FUNC_NAME_IS_MAP(obj) {
        return obj instanceof Map;
    }
"""

fun V8.newMap(): V8Object {
    return executeObjectScript("new Map();")
}
private fun V8Object.check() {
    assert(isMap()) { "Only a Map type V8Object can do this operation" }
}
fun V8Object.isMap(): Boolean {
    if (runtime.getType(JS_FUNC_NAME_IS_MAP) != V8Value.V8_FUNCTION) {
        runtime.executeScript(JS_FUNC_IS_MAP)
    }
    return runtime.executeBooleanFunction(JS_FUNC_NAME_IS_MAP, V8Array(runtime).push(this))
}
fun V8Object.set(key: String, value: Int): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}
fun V8Object.set(key: String, value: Boolean): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}
fun V8Object.set(key: String, value: Double): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}
fun V8Object.set(key: String, value: String): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}
fun V8Object.set(key: String, value: V8Value): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}
fun V8Object.set(key: String, value: Any): V8Object {
    check()
    this.executeFunction("set", V8Array(runtime).push(key).push(value))
    return this
}

fun V8Object.get(key: String): Any {
    check()
    return this.executeFunction("get", V8Array(runtime).push(key))
}

fun V8Object.delete(key: String): Boolean {
    check()
    return this.executeBooleanFunction("delete", V8Array(runtime).push(key))
}

fun V8Object.has(key: String): Boolean {
    check()
    return this.executeBooleanFunction("has", V8Array(runtime).push(key))
}
fun V8Object.clear() {
    check()
    this.executeJSFunction("clear")
}