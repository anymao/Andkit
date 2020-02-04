package com.anymore.wanandroid.repository.interceptor

import android.content.Context
import com.anymore.wanandroid.common.ext.isNetworkConnected
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by liuyuanmao on 2019/4/18.
 */
class CacheControlInterceptor(private val applicationContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!applicationContext.isNetworkConnected()) {//没有网络时候将缓存策略设置为从缓存读取
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response = chain.proceed(request)
        return if (applicationContext.isNetworkConnected()) {//网络可用，按照请求的缓存策略缓存
            val cacheControl = request.cacheControl
            response.newBuilder()
//                .header("Cache-Control", cacheControl.toString())
//                .removeHeader("Pragma")
                .build()
        } else {//网络不可用，将缓存策略设置为30天超时失效
            val maxStale = 60 * 60 * 24 * 30
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
    }
}