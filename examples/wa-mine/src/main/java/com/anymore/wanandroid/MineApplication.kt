package com.anymore.wanandroid

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.annotations.Kiss
import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.di.component.DaggerMineComponent
import com.anymore.wanandroid.mine.BuildConfig

/**
 * Created by anymore on 2020/1/29.
 */
@Kiss(MineApplicationWrapper::class,priority = 99)
class MineApplication:AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerMineComponent.builder()
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