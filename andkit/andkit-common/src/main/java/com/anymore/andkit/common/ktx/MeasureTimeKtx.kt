package com.anymore.andkit.common.ktx

import android.os.SystemClock
import timber.log.Timber

/**
 * 计算代码块耗时
 */
fun <T> measureTime(block: () -> T):T {
    val start = SystemClock.uptimeMillis()
    return block().also {
        Timber.d("MeasureTime Cost:${SystemClock.uptimeMillis()-start} ms")
    }
}