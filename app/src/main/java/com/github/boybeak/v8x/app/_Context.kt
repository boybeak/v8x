package com.github.boybeak.v8x.app

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

fun Context.readAssetsText(assetsPath: String, charset: Charset = Charsets.UTF_8): String {
    return assets.open(assetsPath).reader(charset).run {
        val text = readText()
        close()
        text
    }
}

fun Context.copyAssetsTo(assetsPath: String, dstFile: File) {
    if (dstFile.parentFile?.exists() != true) {
        dstFile.parentFile?.mkdirs()
    }
    val fos = FileOutputStream(dstFile)
    assets.open(assetsPath).run {
        copyTo(fos)
        close()
    }
    fos.flush()
    fos.close()
}