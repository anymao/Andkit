package com.anymore.andkit.repository.di.component

import com.anymore.andkit.repository.IRepositoryManager
import com.anymore.andkit.repository.RepositoryInjector
import com.anymore.andkit.repository.di.module.HttpClientModule
import com.anymore.andkit.repository.di.module.RepositoryConfigsModule
import com.anymore.andkit.repository.di.module.RepositoryModule
import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by liuyuanmao on 2019/3/9.
 */

@Singleton
@Component(modules = [RepositoryModule::class, RepositoryConfigsModule::class, HttpClientModule::class])
interface RepositoryComponent {
    fun getRepository(): IRepositoryManager
    fun okHttpClient(): OkHttpClient
    fun gson(): Gson
    fun inject(injector: RepositoryInjector)
}