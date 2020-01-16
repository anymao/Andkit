package com.anymore.andkit.repository

import android.app.Application
import com.anymore.andkit.repository.di.component.DaggerRepositoryComponent
import com.anymore.andkit.repository.di.component.RepositoryComponent
import com.anymore.andkit.repository.di.module.HttpClientModule
import com.anymore.andkit.repository.di.module.RepositoryConfigsModule
import com.anymore.andkit.repository.di.module.RepositoryModule

/**
 * Created by liuyuanmao on 2019/3/9.
 */
interface IRepositoryInjector {
    fun getRepositoryComponent(): RepositoryComponent
}

class RepositoryInjector constructor(private val application: Application) : IRepositoryInjector {

    private lateinit var mRepositoryComponent: RepositoryComponent

    fun onCreate() {
        val mRepositoryConfigsModule = getRepositoryConfigsModule()
        mRepositoryComponent = DaggerRepositoryComponent
            .builder()
            .repositoryModule(RepositoryModule())
            .repositoryConfigsModule(mRepositoryConfigsModule)
            .httpClientModule(HttpClientModule())
            .build()
    }

    private fun getRepositoryConfigsModule(): RepositoryConfigsModule {
        val repositoryConfigs = ManifestParser.parseRepositoryConfig(application)
        val builder = RepositoryConfigsModule.builder(application)
        for (config in repositoryConfigs) {
            config.applyConfig(application, builder)
        }
        return builder.build()
    }

    override fun getRepositoryComponent() = mRepositoryComponent
}