package com.anymore.wanandroid.entry

/**
 * 对请求文章列表的封装
 */
data class Articles(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)