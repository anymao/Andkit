package com.anymore.andkit.common.ktx

import com.anymore.andkit.common.ktx.ExceptionTrackerHolder.tracker

/**
 * 统一追踪被捕捉的异常
 * 例如我们在开发环境下打印出来
 * 在线上环境进行上报
 */
object ExceptionTrackerHolder {
    @JvmStatic
    @Volatile
    var tracker: ((Throwable?) -> Unit)? = null
}

inline fun <T> tryOr(default: T, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    tracker?.invoke(e)
    default
}

inline fun <T> tryOrNull(block: () -> T?): T? = try {
    block()
} catch (e: Throwable) {
    tracker?.invoke(e)
    null
}

inline fun tryOrNothing(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        tracker?.invoke(e)
    }
}