package com.anymore.baike.bean

/**
 * Created by lym on 2020/11/13.
 */
data class BaikeResult(
    val resultCode: Int,
    val keyWords: String,
    val baikeContent: String,
    val baikeUrl: String
)