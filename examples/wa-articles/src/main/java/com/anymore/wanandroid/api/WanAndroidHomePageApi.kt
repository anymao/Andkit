package com.anymore.wanandroid.api

import com.anymore.wanandroid.entry.Banner
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by anymore on 2020/1/27.
 */
interface WanAndroidHomePageApi {
    @GET("/banner/json")
    fun getBanner(): Observable<WanAndroidResponse<List<Banner>>>
}