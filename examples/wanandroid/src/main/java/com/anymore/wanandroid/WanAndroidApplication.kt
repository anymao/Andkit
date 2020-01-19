package com.anymore.wanandroid

import android.content.Context
import com.anymore.andkit.AndkitApplication
import com.anymore.wanandroid.di.component.DaggerUserModuleComponent
import com.anymore.wanandroid.di.component.DaggerWanAndroidComponent
import com.anymore.wanandroid.di.module.UserModule

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class WanAndroidApplication : AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerWanAndroidComponent.builder()
            .userModuleComponent(DaggerUserModuleComponent.builder().userModule(UserModule(this)).build())
            .userModule(UserModule(this))
            .build()
            .inject(this)
    }

}