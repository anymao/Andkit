package com.anymore.wanandroid.common.exceptions

import timber.log.Timber

/**
 * Created by liuyuanmao on 2020/4/7.
 */
interface ErrorHandler {
    fun handle(throwable: Throwable): Boolean

    object EmptyErrorHandler : ErrorHandler {
        override fun handle(throwable: Throwable): Boolean {
            Timber.e(throwable)
            return true
        }

    }
}