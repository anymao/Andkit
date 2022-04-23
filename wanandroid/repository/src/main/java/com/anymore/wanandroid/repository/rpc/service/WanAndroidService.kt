package com.anymore.wanandroid.repository.rpc.service

import com.anymore.andkit.rpc.converter.HuskWith
import com.anymore.wanandroid.repository.rpc.response.HomeBannerVo
import com.anymore.wanandroid.repository.rpc.response.LoginVo
import com.anymore.wanandroid.repository.rpc.response.WanAndroidBaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by anymore on 2022/3/30.
 */
interface WanAndroidService {

    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): LoginVo?

    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginVo?


    @HuskWith(WanAndroidBaseResponse::class)
    @POST("/user/logout/json")
    suspend fun logout(): Any?


    @HuskWith(WanAndroidBaseResponse::class)
    @GET("/banner/json")
    suspend fun getHomeBanners(): List<HomeBannerVo>?
}