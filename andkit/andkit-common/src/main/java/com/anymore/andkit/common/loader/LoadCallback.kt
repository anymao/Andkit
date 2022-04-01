package com.anymore.andkit.common.loader

/**
 * Created by anymore on 2022/3/29.
 */
interface LoadCallback {
    fun onPrepare()
    fun onSuccess()
    fun onFailed(throwable: Throwable?): Boolean
    fun onFinal()
}