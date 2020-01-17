package com.anymore.wanandroid.repository.base

data class WanAndroidResponse<D>(
    val errorCode: Int,
    val errorMsg: String?,
    val data: D?
)