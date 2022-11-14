package com.anymore.andkit.common.ktx

import timber.log.Timber

val screenWidth: Int by lazy { applicationContext.resources.displayMetrics.widthPixels }

val screenHeight: Int by lazy { applicationContext.resources.displayMetrics.heightPixels }

/**
 * 获取状态栏高度
 */
val statusBarHeight: Int by lazy {
    val resources = applicationContext.resources
    try {
        var result = 0
        val resourceId: Int =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return@lazy if (result > 0) {
            Timber.d("获取系统状态栏高度：$result")
            result
        } else {
            result = 0
            Timber.w("无法获取系统状态栏高度，使用默认高度：$result")
            result
        }
    } catch (e: Exception) {
        val result = 0
        Timber.e(e, "无法获取系统状态栏高度，使用默认高度：$result")
        return@lazy result
    }
}