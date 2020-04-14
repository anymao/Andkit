package com.anymore.wanandroid.mine.api

import com.anymore.wanandroid.mine.entry.Article
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * 收藏相关接口
 */
interface WanAndroidCollectApi {

    //获取所有收藏的文章
    @GET("/lg/collect/list/{page}/json")
    fun getAllCollectedArticles(@Path("page") page: Int): Observable<WanAndroidResponse<PagedData<Article>>>

    //收藏站内指定id文章
    @POST("/lg/collect/{id}/json")
    fun collectWanAndroidArticle(@Path("id") id: Int): Observable<WanAndroidResponse<Any>>

    //收藏站外文章
    @POST("/lg/collect/add/json")
    fun collectOtherArticle(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Observable<WanAndroidResponse<Any>>

    //取消收藏方式1
    @POST("/lg/uncollect_originId/{id}/json")
    fun uncollectWanAndroidArticle(@Path("id") id: Int): Observable<WanAndroidResponse<Any>>

    //取消收藏方式2
    @POST("/lg/uncollect/{id}/json")
    fun uncollectArticle(@Path("id") id: Int, @Field("originId") originId: Int = -1): Observable<WanAndroidResponse<Any>>

}