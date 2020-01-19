package com.anymore.wanandroid

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication
import com.anymore.wanandroid.di.component.DaggerWanAndroidComponent
import com.anymore.wanandroid.di.module.UserModule
import com.anymore.wanandroid.user.BuildConfig

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class WanAndroidApplication : AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerWanAndroidComponent.builder()
            .userModule(UserModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

}