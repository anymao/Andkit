package com.anymore.wanandroid.repository.rpc

import com.anymore.andkit.rpc.converter.HuskResponseConverter
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by anymore on 2022/3/31.
 */
@Module
@InstallIn(SingletonComponent::class)
object Repository {


    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient() = kotlin.run {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = kotlin.run {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(HuskResponseConverter.factory(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWanAndroidService(retrofit: Retrofit):WanAndroidService = retrofit.create()


}