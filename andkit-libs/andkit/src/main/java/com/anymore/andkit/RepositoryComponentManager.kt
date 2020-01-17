package com.anymore.andkit

import android.app.Application
import com.anymore.andkit.di.component.DaggerAndkitComponent
import com.anymore.andkit.repository.IRepositoryComponentProvider
import com.anymore.andkit.repository.RepositoryInjector
import com.anymore.andkit.repository.di.component.RepositoryComponent

/**
 * Created by liuyuanmao on 2020/1/16.
 */
object RepositoryComponentManager:IRepositoryComponentProvider {


    private lateinit var mApplication: Application
    private val mRepositoryInjector by lazy { RepositoryInjector(mApplication) }

    fun install(application: Application) {
        mApplication = application
        check(application is AndkitApplication) {
            "the application must be subclass of<${AndkitApplication::class.java.name}>"
        }
        DaggerAndkitComponent.create().inject(application)
    }

    fun onCreate() {
        mRepositoryInjector.onCreate()
    }

    override val repositoryComponent: RepositoryComponent
        get() = mRepositoryInjector.getRepositoryComponent()
}