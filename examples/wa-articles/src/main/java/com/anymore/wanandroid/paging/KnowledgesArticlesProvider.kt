package com.anymore.wanandroid.paging

import com.anymore.wanandroid.api.WanAndroidKnowledgeApi
import com.anymore.wanandroid.entry.Articles
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable

class KnowledgesArticlesProvider(private val api: WanAndroidKnowledgeApi, private val cid: Int) :
    ArticlesProvider {

    override fun loadInitial(page: Int): Observable<Articles> {
        return api.getSubKnowledges(page, cid)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取${cid}下知识体系文章列表失败!")
                }
            }
    }

    override fun loadAfter(page: Int): Observable<Articles> {
        return api.getSubKnowledges(page, cid)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取${cid}下知识体系文章列表失败!")
                }
            }
    }

    override fun loadBefore(page: Int): Observable<Articles> {
        return api.getSubKnowledges(page, cid)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map it.data
                } else {
                    throw WanAndroidException(it.errorMsg ?: "获取${cid}下知识体系文章列表失败!")
                }
            }
    }
}