package com.anymore.wanandroid.common.exceptions

/**
 * Created by liuyuanmao on 2020/4/7.
 */
interface ErrorHandler {
    fun handle(throwable: Throwable):Boolean
}