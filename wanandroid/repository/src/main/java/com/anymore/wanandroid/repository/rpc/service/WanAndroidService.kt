package com.anymore.wanandroid.repository.rpc.service

import com.anymore.andkit.rpc.converter.HuskWith
import com.anymore.wanandroid.repository.rpc.request.LoginBody
import com.anymore.wanandroid.repository.rpc.request.RegisterBody
import com.anymore.wanandroid.repository.rpc.response.HomeBannerVo
import com.anymore.wanandroid.repository.rpc.response.LoginVo
import com.anymore.wanandroid.repository.rpc.response.WanAndroidBaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by anymore on 2022/3/30.
 */
interface WanAndroidService {

    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/register")
    suspend fun register(@Body body: RegisterBody): LoginVo?

    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/login")
    suspend fun login(@Body body: LoginBody): LoginVo?


    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/logout/json")
    suspend fun logout(): Any?


    @HuskWith(WanAndroidBaseResponse::class)
    @GET("/banner/json")
    suspend fun getHomeBanners(): List<HomeBannerVo>?
}