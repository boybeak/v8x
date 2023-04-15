package com.github.boybeak.v8x.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.model.User
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.binding.ext.manager
import com.github.boybeak.v8x.binding.ext.registerV8Methods

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val v8 = V8.createV8Runtime()
        val native = Native(v8)
        val nativeV8 = V8Object(v8)
        nativeV8.registerV8Methods(native)
        v8.add("native", nativeV8)
        findViewById<AppCompatButton>(R.id.actionBtn).setOnClickListener {
            v8.executeScript(this@MainActivity.readAssetsText("demo.js"))
        }
        findViewById<AppCompatButton>(R.id.nodeJsBtn).setOnClickListener {
            startActivity(Intent(this, NodeJSActivity::class.java))
        }
        findViewById<AppCompatButton>(R.id.webglBtn).setOnClickListener {
            startActivity(Intent(this, WebGLActivity::class.java))
        }
        findViewById<AppCompatButton>(R.id.gameBtn).setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        findViewById<AppCompatButton>(R.id.bifBtn).setOnClickListener {
            val man = v8.manager
            v8.executeScript(this@MainActivity.readAssetsText("build_in_fields.js"))
        }
        findViewById<AppCompatButton>(R.id.mapBtn).setOnClickListener {
            v8.executeScript("""
                const user1 = native.getUser();
                const user2 = native.getUser();
                console.log(user1 == user2);
            """.trimIndent())
        }
    }

    private class Native(val v8: V8) {
        val user = User()
        @V8Method
        fun getUser(): V8Object {
            return user.getMyBinding(v8)
        }
    }

}