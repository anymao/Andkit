package com.anymore.wanandroid.repository

import android.content.Context
import android.util.SparseArray
import com.anymore.andkit.di.RepositoryConfiguration
import com.anymore.andkit.repository.configs.OkHttpConfig
import com.anymore.andkit.repository.configs.RepositoryConfig
import com.anymore.wanandroid.repository.cookies.PersistentCookieJar
import com.anymore.wanandroid.repository.cookies.SharedPreferencesCookieStore
import com.anymore.wanandroid.repository.interceptor.CacheControlInterceptor
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/3/12.
 */
@RepositoryConfiguration
class WanAndroidRepositoryConfig @Inject constructor() : RepositoryConfig {

    companion object {
        const val MAX_CACHE_SIZE = 30 * 1024 * 1024L
    }

    override fun okHttpConfig(): OkHttpConfig {
        return object : OkHttpConfig {
            override fun provideUrls() = SparseArray<HttpUrl>().apply {
                put(WAN_ANDROID_KEY, WAN_ANDROID_BASE_URL.toHttpUrl())
            }

            override fun config(context: Context, builder: OkHttpClient.Builder) {
                builder.cache(provideOkCache(context, MAX_CACHE_SIZE))
                val cookieStore = SharedPreferencesCookieStore(context)
                builder.cookieJar(PersistentCookieJar(cookieStore))
                val okLogger = HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
                builder
//                        .addNetworkInterceptor(HeadersInterceptor())//为请求添加Headers
                    .addInterceptor(CacheControlInterceptor(context))
                    .addInterceptor(okLogger)
            }

        }
    }


    private fun provideOkCache(context: Context, maxSize: Long): Cache {
        val cacheDir = File(context.cacheDir, "okCache")
        return Cache(cacheDir, maxSize)
    }


}