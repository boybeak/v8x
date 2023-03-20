package com.github.boybeak.v8x.app.adapter.item

import com.github.boybeak.adapter.AbsHolder
import com.github.boybeak.adapter.AbsItem
import com.github.boybeak.adapter.ItemImpl
import com.github.boybeak.v8x.R
import com.github.boybeak.v8x.app.adapter.holder.AssetsJsHolder

class AssetsJsItem(val path: String, s: String) : AbsItem<String>(s) {
    override fun layoutId(): Int {
        return R.layout.item_assets_js
    }

    override fun holderClass(): Class<AssetsJsHolder> {
        return AssetsJsHolder::class.java
    }

    override fun areContentsSame(other: ItemImpl<*>): Boolean {
        return source() == other.source()
    }
}