package com.github.boybeak.v8x.app.adapter

import android.content.Context
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.v8x.app.adapter.holder.AssetsJsHolder
import com.github.boybeak.v8x.app.adapter.item.AssetsJsItem

class AssetsAdapter(context: Context, path: String) : AnyAdapter() {
    init {
        addAll(context.assets.list(path)?.asList() ?: emptyList()) { s, t ->
            AssetsJsItem(path, s)
        }
    }
}