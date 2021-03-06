package com.anymore.wanandroid.articles.api

import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit 文章列表请求接口
 * Created by anymore on 2020/1/25.
 */
interface WanAndroidArticlesApi {

    @GET("/article/list/{page}/json")
    fun getHomePageArticles(@Path("page") page: Int): Observable<WanAndroidResponse<PagedData<Article>>>
}