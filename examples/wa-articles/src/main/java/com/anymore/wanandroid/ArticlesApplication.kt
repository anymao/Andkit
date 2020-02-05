package com.anymore.wanandroid

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.annotations.Kiss
import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.articles.BuildConfig
import com.anymore.wanandroid.di.component.DaggerArticlesModuleComponent

/**
 * Created by anymore on 2020/1/25.
 */
@Kiss(value = ArticlesApplicationWrapper::class, priority = 99)
class ArticlesApplication : AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerArticlesModuleComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }
}