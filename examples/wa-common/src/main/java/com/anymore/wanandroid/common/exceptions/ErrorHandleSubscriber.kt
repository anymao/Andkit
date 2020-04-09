package com.anymore.wanandroid.common.exceptions

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by liuyuanmao on 2020/4/7.
 */
abstract class ErrorHandleSubscriber<T>(val errorHandler: ErrorHandler = DEFAULT_ERROR_HANDLER) :
    Observer<T> {
    companion object {
        var DEFAULT_ERROR_HANDLER: ErrorHandler = ErrorHandler.EmptyErrorHandler
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        errorHandler.handle(e)
    }
}