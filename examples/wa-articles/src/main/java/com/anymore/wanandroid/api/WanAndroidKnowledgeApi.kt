package com.anymore.wanandroid.api

import com.anymore.wanandroid.entry.Article
import com.anymore.wanandroid.entry.Knowledge
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidKnowledgeApi {

    //获取所有知识体系
//    @Headers("Cache-Control:public,max-age=6000000")
    @GET("/tree/json")
    fun getAllKnowledges(): Observable<WanAndroidResponse<List<Knowledge>>>

    //获取某个知识体系下的所有文章
//    @Headers("Cache-Control:public,max-age=6000000")
    @GET("/article/list/{page}/json")
    fun getSubKnowledges(@Path("page") page: Int, @Query("cid") cid: Int): Observable<WanAndroidResponse<PagedData<Article>>>
}