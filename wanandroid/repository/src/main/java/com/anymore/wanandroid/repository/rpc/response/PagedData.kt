package com.anymore.wanandroid.repository.rpc.response


import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class PagedData<T : Serializable>(
    var curPage: Int? = null,
    var datas: List<T>? = null,
    var offset: Int? = null,
    var over: Boolean? = null,
    var pageCount: Int? = null,
    var size: Int? = null,
    var total: Int? = null
) : Serializable