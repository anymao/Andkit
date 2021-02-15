package com.anymore.andkit.repository.di

import android.app.Application
import android.content.Context
import android.util.SparseArray
import com.anymore.andkit.repository.IRepositoryManager
import com.anymore.andkit.repository.RepositoryManager
import com.anymore.andkit.repository.configs.*
import com.anymore.cachekit.DataCache
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by liuyuanmao on 2019/3/7.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideIRepositoryManager(
        context: Context,
        retrofits: Lazy<SparseArray<Retrofit>>,
        roomDatabaseConfig: RoomDatabaseConfig,
        dataCache: Lazy<DataCache>,
        okHttpClient: Lazy<OkHttpClient>
    ): IRepositoryManager {
        return RepositoryManager(
            context.applicationContext as Application,
            retrofits,
            roomDatabaseConfig,
            dataCache,
            okHttpClient
        )
    }

//    @Singleton
//    @Provides
//    fun provideRepositoryConfig(context: Context): RepositoryConfig {
//        return ManifestParser.parseRepositoryConfig(context).firstOrNull()
//            ?: RepositoryConfig.DEFAULT
//    }

    @Singleton
    @Provides
    fun provideOkHttpConfig(config: RepositoryConfig) = config.okHttpConfig()

    @Singleton
    @Provides
    fun provideUrls(config: OkHttpConfig) = config.provideUrls()

    @Singleton
    @Provides
    fun provideGsonConfig(config: RepositoryConfig) = config.gsonConfig()

    @Singleton
    @Provides
    fun provideRetrofitConfig(config: RepositoryConfig) = config.retrofitConfig()

    @Singleton
    @Provides
    fun provideDataCacheConfig(config: RepositoryConfig) = config.dataCacheConfig()

    @Singleton
    @Provides
    fun provideRoomDatabaseConfig(config: RepositoryConfig) = config.roomDatabaseConfig()

    @Provides
    fun provideDataCacheBuilder(): DataCache.Builder = DataCache.Builder()

    @Singleton
    @Provides
    fun provideDataCache(
        context: Context,
        builder: DataCache.Builder,
        config: DataCacheConfig
    ): DataCache {
        config.config(context, builder)
        return builder.build()
    }

    @Provides
    fun provideRetrofitBuilder() = Retrofit.Builder()

    @Provides
    fun provideRetrofits(
        context: Context,
        builder: Retrofit.Builder,
        client: OkHttpClient,
        config: RetrofitConfig?,
        baseUrls: SparseArray<HttpUrl>
    ): SparseArray<Retrofit> {
        //builder....
        Timber.d(baseUrls.toString())
        builder.client(client)
        config?.config(context, builder)
        val retrofits = SparseArray<Retrofit>()
        for (i in 0 until baseUrls.size()) {
            val key = baseUrls.keyAt(i)
            val url = baseUrls.get(key)
            val retrofit = builder.baseUrl(url).build()
            retrofits.put(key, retrofit)
        }
        return retrofits
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder() = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context,
        builder: OkHttpClient.Builder,
        config: OkHttpConfig?
    ): OkHttpClient {
        config?.config(context, builder)
        return builder.build()
    }

    @Provides
    fun provideGsonBuilder() = GsonBuilder()

    @Provides
    fun provideGson(
        context: Context,
        builder: GsonBuilder,
        config: GsonConfig
    ): Gson {
        config.config(context, builder)
        return builder.create()
    }

}