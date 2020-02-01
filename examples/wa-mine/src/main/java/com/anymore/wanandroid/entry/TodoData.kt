package com.anymore.wanandroid.entry

data class TodoData(
    val curPage: Int,
    val datas: List<Todo>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)