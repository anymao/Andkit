package com.anymore.wanandroid.repository.base

import java.io.Serializable

/**
 * Created by anymore on 2020/2/2.
 */
data class PagedData<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) : Serializable