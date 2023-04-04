package com.github.boybeak.v8x.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.github.boybeak.canvas.CanvasView
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.copyAssetsTo
import com.github.boybeak.v8x.canvas.v8.V8WebCanvasView
import java.io.File

class WebGLFragment(private val assetsPath: String) : DialogFragment(R.layout.fragment_webgl) {

    companion object {
        private const val TAG = "WebGLFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dir = File(requireActivity().externalCacheDir, "webgl")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val jsFile = File(dir, assetsPath.substringAfterLast(File.separatorChar))
        if (jsFile.exists()) {
            jsFile.delete()
        }
        requireActivity().copyAssetsTo(assetsPath, jsFile)
        val webGLView = view.findViewById<V8WebCanvasView>(R.id.webGLView)
        webGLView.addCallback(object : CanvasView.Callback {
            override fun onSurfaceCreated() {
                Log.d(TAG, "onSurfaceCreated")
                webGLView.start(jsFile)
            }

            override fun onSurfaceDestroyed() {
                Log.d(TAG, "onSurfaceDestroyed")
            }

            override fun onThreadCreated() {
                Log.d(TAG, "onThreadCreated")
            }

            override fun onThreadDestroyed() {
                Log.d(TAG, "onThreadDestroyed")
            }
        })
        Log.d(TAG, "isSurfaceAttached=${webGLView.isAttachedToWindow}")
        view.findViewById<AppCompatButton>(R.id.actionBtn).setOnClickListener {
            webGLView.requestRender()
        }
    }
}