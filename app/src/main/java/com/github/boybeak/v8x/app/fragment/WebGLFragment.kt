package com.github.boybeak.v8x.app.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.copyAssetsTo
import com.github.boybeak.v8x.gl.V8GLView
import java.io.File

class WebGLFragment(private val assetsPath: String) : DialogFragment(R.layout.fragment_webgl) {
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
        val webGLView = view.findViewById<V8GLView>(R.id.webGLView)
        webGLView.start(jsFile)
    }
}