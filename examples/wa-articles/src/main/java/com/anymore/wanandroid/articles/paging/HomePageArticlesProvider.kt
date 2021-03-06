package com.anymore.wanandroid.articles.paging

import com.anymore.wanandroid.articles.api.WanAndroidArticlesApi
import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable

/**
 * Created by anymore on 2020/1/25.
 */
class HomePageArticlesProvider(private val api: WanAndroidArticlesApi) :
    ArticlesProvider {

    override fun loadInitial(page: Int): Observable<PagedData<Article>> {
        return api.getHomePageArticles(page)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取首页文章列表失败!")
                }
            }
    }

    override fun loadAfter(page: Int): Observable<PagedData<Article>> {
        return api.getHomePageArticles(page)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取首页文章列表失败!")
                }
            }
    }

    override fun loadBefore(page: Int): Observable<PagedData<Article>> {
        return api.getHomePageArticles(page)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取首页文章列表失败!")
                }
            }
    }

}