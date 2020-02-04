package com.anymore.wanandroid.repository

import android.content.Context
import androidx.room.RoomDatabase
import com.anymore.andkit.repository.di.module.RepositoryConfigsModule
import com.anymore.wanandroid.repository.cookies.PersistentCookieJar
import com.anymore.wanandroid.repository.cookies.SharedPreferencesCookieStore
import com.anymore.wanandroid.repository.interceptor.CacheControlInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File

/**
 * Created by liuyuanmao on 2019/3/12.
 */
class WanAndroidRepositoryConfig : RepositoryConfigsModule.RepositoryConfig {

    companion object {
        const val MAX_CACHE_SIZE = 30 * 1024 * 1024L
    }

    override fun applyConfig(context: Context, builder: RepositoryConfigsModule.Builder) {
        Timber.i("applyConfig ....start")
        builder.apply {
            addUrl(WAN_ANDROID_KEY, WAN_ANDROID_BASE_URL)
            okHttpConfig = object : RepositoryConfigsModule.OkHttpConfig {

                override fun applyConfig(context: Context, builder: OkHttpClient.Builder) {
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
                        .addNetworkInterceptor(CacheControlInterceptor(context))
                        .addNetworkInterceptor(okLogger)
                }

            }
            roomDatabaseConfig = object : RepositoryConfigsModule.RoomDatabaseConfig {
                override fun config(context: Context, builder: RoomDatabase.Builder<*>) {

                }

            }
        }
        Timber.i("applyConfig ....end")
    }


    private fun provideOkCache(context: Context, maxSize: Long): Cache {
        val cacheDir = File(context.cacheDir, "okCache")
        return Cache(cacheDir, maxSize)
    }
}