package com.anymore.wanandroid.repository.rpc.service

import com.anymore.andkit.rpc.converter.HuskWith
import com.anymore.wanandroid.repository.rpc.response.HomeBannerVo
import com.anymore.wanandroid.repository.rpc.response.WanAndroidBaseResponse
import retrofit2.http.GET

/**
 * Created by anymore on 2022/3/30.
 */
interface WanAndroidService {

    @HuskWith(WanAndroidBaseResponse::class,codes = [0L])
    @GET("/banner/json")
    suspend fun getHomeBanners(): List<HomeBannerVo>
}