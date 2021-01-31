package com.anymore.andkit.repository.configs

import android.content.Context
import android.util.SparseArray
import com.anymore.andkit.repository.BuildConfig
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface OkHttpConfig : BaseConfig<OkHttpClient.Builder> {

    fun provideUrls(): SparseArray<HttpUrl>

    companion object {
        /**
         * default impl
         */
        val DEFAULT = object : OkHttpConfig {
            override fun provideUrls() = SparseArray<HttpUrl>().apply {
                put(1, "http://www.wanandroid.com".toHttpUrlOrNull())
            }

            override fun config(context: Context, builder: OkHttpClient.Builder) {
                val okLogger = HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
                builder.addInterceptor(okLogger)
            }
        }
    }

}