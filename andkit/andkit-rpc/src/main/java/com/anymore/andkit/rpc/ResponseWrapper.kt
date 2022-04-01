package com.anymore.andkit.rpc

/**
 * Created by anymore on 2022/3/30.
 */
interface ResponseWrapper<T> {
    val code: Long?
    val message: String?
    val `data`: T?

    @Throws(ErrorResponseException::class)
    fun check(vararg codes: Long)
}