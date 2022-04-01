package com.anymore.andkit.common.ktx

import timber.log.Timber

/**
 * Created by anymore on 2022/3/29.
 */
var exceptionTracker: (Throwable, String?) -> Unit = { e, m -> Timber.e(e, m) }

inline fun <T> tryOr(default: T, block: () -> T): T {
    return try {
        block()
    } catch (e: Throwable) {
        exceptionTracker(e, "[tryOr] catch and return $default")
        default
    }
}

inline fun <T> tryOrNull(block: () -> T?): T? {
    return try {
        block()
    } catch (e: Throwable) {
        exceptionTracker(e, "[tryOrNull] catch and return null")
        null
    }
}

inline fun tryOrNothing(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        exceptionTracker(e, "[tryOrNothing] catch")
    }
}