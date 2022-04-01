package com.anymore.andkit.common.loader

/**
 * Created by anymore on 2022/3/29.
 */
interface Loader {

    var loader: (() -> Unit)?

    fun load() {
        loader?.invoke()
    }
}