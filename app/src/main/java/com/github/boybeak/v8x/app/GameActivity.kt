package com.github.boybeak.v8x.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.gl.V8GLView
import java.io.File

class GameActivity : AppCompatActivity() {

    private val v8glView: V8GLView by lazy { findViewById(R.id.v8glView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameJsFile = File(externalCacheDir, "game.js")
        if (gameJsFile.exists()) {
            gameJsFile.delete()
        }
        copyAssetsTo("game.js", gameJsFile)
        v8glView.start(gameJsFile)
    }
}