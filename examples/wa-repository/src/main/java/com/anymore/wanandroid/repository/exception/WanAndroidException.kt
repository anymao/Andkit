package com.anymore.wanandroid.repository.exception

/**
 * 后台数据接口异常类
 */
class WanAndroidException : RuntimeException {
    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

}