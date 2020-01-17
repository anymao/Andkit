package com.anymore.wanandroid.repository.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by liuyuanmao on 2019/6/20.
 */
class HeadersInterceptor:Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl.Builder()
            .maxAge(1,TimeUnit.DAYS)
            .maxStale(1,TimeUnit.HOURS)
            .build()
        val request = chain.request()
            .newBuilder()
            .cacheControl(cacheControl)
            .build()
        return chain.proceed(request)
    }

}