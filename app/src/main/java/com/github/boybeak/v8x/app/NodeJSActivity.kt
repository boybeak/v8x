package com.github.boybeak.v8x.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eclipsesource.v8.NodeJS
import com.eclipsesource.v8.V8
import com.github.boybeak.v8x.R
import java.io.File

class NodeJSActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node_js)

        val dir = File(externalCacheDir, "nodejs")
        val utilsJs = File(dir, "utils.js")
        copyAssetsTo("nodejs/util.js", utilsJs)
        val demoJs = File(dir, "demo.js")
        copyAssetsTo("nodejs/demo.js", demoJs)

        val v8 = V8.createV8Runtime("global", dir.absolutePath)
        v8.executeScript("global.__run(require, exports, module, __filename, __dirname);")
        val a = V8Pro

    }
}