package com.anymore.wanandroid

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.di.component.DaggerUserModuleComponent
import com.anymore.wanandroid.user.BuildConfig

/**
 * Created by liuyuanmao on 2020/1/19.
 */
class UserApplication : AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerUserModuleComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build().inject(this)

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