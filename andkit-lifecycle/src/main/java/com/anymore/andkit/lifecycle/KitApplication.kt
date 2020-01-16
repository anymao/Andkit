package com.anymore.andkit.lifecycle

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.anymore.andkit.lifecycle.application.ApplicationWrapperMonitor
import com.anymore.andkit.lifecycle.application.InternalApplicationWrapper
import com.anymore.andkit.lifecycle.application.Kiss
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/2/20.
 */
@Kiss(value = InternalApplicationWrapper::class, priority = Int.MAX_VALUE)
open class KitApplication : Application(), HasAndroidInjector{


    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        timberPlant()
        //仓储层注入相关初始化
//        RepositoryComponentProvider.install(this)
        ApplicationWrapperMonitor.install(this)
        ApplicationWrapperMonitor.attachBaseContext()
    }

    override fun onCreate() {
        super.onCreate()
//        RepositoryComponentProvider.onCreate()
        ApplicationWrapperMonitor.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        ApplicationWrapperMonitor.onTerminate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ApplicationWrapperMonitor.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ApplicationWrapperMonitor.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        ApplicationWrapperMonitor.onTrimMemory(level)
    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector

    /**
     * 初始化[Timber]debug日志组件，
     * 子类可覆盖此方法使用自己的日志打印实现
     */
    protected open fun timberPlant() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}