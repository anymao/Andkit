package com.anymore.wanandroid.repository.paging

import io.reactivex.Observable

/**
 * 使用Provider将真实的数据请求细节屏蔽，统一那些仅仅是获取方式不同，而输出结果格式一致的api接口
 * Created by anymore on 2020/1/25.
 */
interface PagingProvider<T> {
    fun loadInitial(page: Int): Observable<T>
    fun loadAfter(page: Int): Observable<T>
    fun loadBefore(page: Int): Observable<T>
}