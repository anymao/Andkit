package com.anymore.wanandroid.common.ext

import android.view.View

/**
 * Created by anymore on 2020/11/15.
 */
fun View.click(block: (() -> Unit)?) {
    if (block == null) {
        setOnClickListener(null)
    } else {
        setOnClickListener { block() }
    }
}