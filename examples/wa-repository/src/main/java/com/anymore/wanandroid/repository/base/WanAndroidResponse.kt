package com.anymore.wanandroid.repository.base

import java.io.Serializable

data class WanAndroidResponse<D>(
    val errorCode: Int,
    val errorMsg: String?,
    val data: D?
) : Serializable