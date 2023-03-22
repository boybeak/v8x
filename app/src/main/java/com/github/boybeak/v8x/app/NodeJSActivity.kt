package com.github.boybeak.v8x.app

import android.net.Uri
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
        dir.deleteRecursively()
        val utilsJs = File(dir, "utils.js")
        copyAssetsTo("nodejs/util.js", utilsJs)
        val demoJs = File(dir, "demo.js")
        copyAssetsTo("nodejs/demo.js", demoJs)
        val pkgJson = File(dir, "package.json")
        copyAssetsTo("nodejs/package.json", pkgJson)

        val v8 = V8.createV8Runtime()
        v8.executeScript(demoJs.readText(), Uri.encode(dir.absolutePath))

    }
}