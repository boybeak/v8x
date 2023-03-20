package com.github.boybeak.v8x.app.adapter.holder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.github.boybeak.adapter.AbsHolder
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.adapter.item.AssetsJsItem

class AssetsJsHolder(v: View) : AbsHolder<AssetsJsItem>(v) {

    private val nameTv = v.findViewById<AppCompatTextView>(R.id.name)

    override fun onBind(item: AssetsJsItem, position: Int, absAdapter: AnyAdapter) {
        nameTv.text = item.source()
    }
}