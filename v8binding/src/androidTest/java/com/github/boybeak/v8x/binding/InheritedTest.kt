package com.github.boybeak.v8x.binding

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eclipsesource.v8.V8
import com.github.boybeak.v8x.binding.annotation.V8Method
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InheritedTest {

    companion object {
        private const val TAG = "InheritedTest"
    }

    open class Father : V8Binding {
        override fun getBindingId(): String {
            return hashCode().toString()
        }
        @V8Method
        fun show() {}
    }
    class Son : Father() {}

    @Test
    fun testInherited() {
        val v8 = V8.createV8Runtime()
        val obj = Son().getMyBinding(v8)
        v8.add("a", obj)
        v8.executeScript("a.show();")
        println("obj.keys=[${obj.keys.joinToString()}]")
        Son::class.java.methods.forEach {

        }
    }
}