package com.github.boybeak.v8x.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.v8.NodeJS
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnClick
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.adapter.AssetsAdapter
import com.github.boybeak.v8x.app.adapter.item.AssetsJsItem
import com.github.boybeak.v8x.app.fragment.WebGLFragment
import java.io.File

class WebGLActivity : AppCompatActivity() {

    private val webGLRv: RecyclerView by lazy {
        findViewById(R.id.web_gl_rv)
    }
    private val adapter by lazy {
        AssetsAdapter(this, "webgl").apply {
            setOnClickFor(AssetsJsItem::class.java, object : OnClick<AssetsJsItem> {
                override fun getClickableIds(): IntArray {
                    return intArrayOf(0)
                }

                override fun onClick(
                    view: View,
                    item: AssetsJsItem,
                    position: Int,
                    adapter: AnyAdapter
                ) {
                    showWebGL("${item.path}${File.separator}${item.source()}")
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_gl)

        val utilJs = File(externalCacheDir, "webgl/util.js")
        utilJs.parentFile?.mkdirs()
        copyAssetsTo("util.js", utilJs)

        webGLRv.adapter = adapter
    }

    private fun showWebGL(assetsPath: String) {
        WebGLFragment(assetsPath).show(supportFragmentManager, assetsPath)
    }

}