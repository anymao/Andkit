package com.anymore.wanandroid.repository.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.anymore.wanandroid.repository.base.NetStatus

/**
 * Created by liuyuanmao on 2019/4/19.
 */
typealias Retry = (() -> Unit)

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val status: LiveData<NetStatus>,
    val retry: LiveData<Retry?>
)