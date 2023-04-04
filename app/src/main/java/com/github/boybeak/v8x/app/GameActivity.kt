package com.github.boybeak.v8x.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.widget.AppCompatButton
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.canvas.v8.V8WebCanvasView
import java.io.File

class GameActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GameActivity"
    }

    private val v8glView: SurfaceView by lazy { findViewById(R.id.v8glView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        v8glView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceCreated A")
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "surfaceChanged A")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceDestroyed A")
            }
        })
        findViewById<AppCompatButton>(R.id.actionBtn).setOnClickListener {
            v8glView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    Log.d(TAG, "surfaceCreated B")
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                    Log.d(TAG, "surfaceChanged B")
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    Log.d(TAG, "surfaceDestroyed B")
                }
            })
        }
    }
}