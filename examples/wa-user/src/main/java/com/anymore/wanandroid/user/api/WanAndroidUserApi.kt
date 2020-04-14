package com.anymore.wanandroid.user.api

import com.anymore.wanandroid.repository.base.WanAndroidResponse
import com.anymore.wanandroid.repository.database.entry.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户接口
 */
interface WanAndroidUserApi {

    /**
     * 注册接口
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String, @Field("password") password: String, @Field(
            "repassword"
        ) repassword: String
    ): WanAndroidResponse<UserInfo>

    /**
     * 登陆
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): WanAndroidResponse<UserInfo>

    /**
     * 注销
     */
    @GET("/user/logout/json")
    suspend fun logout(): WanAndroidResponse<String>

}