package com.anymore.andkit.rpc

/**
 * Created by anymore on 2022/3/30.
 */
class ErrorResponseException(val code: Long? = null, message: String? = null) :
    RuntimeException(message)