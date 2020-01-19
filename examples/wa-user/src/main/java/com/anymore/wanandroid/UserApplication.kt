package com.anymore.wanandroid

import android.content.Context
import com.anymore.andkit.AndkitApplication
import com.anymore.wanandroid.di.component.DaggerUserModuleComponent
import com.anymore.wanandroid.di.module.UserModule

/**
 * Created by liuyuanmao on 2020/1/19.
 */
class UserApplication :AndkitApplication(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerUserModuleComponent.builder()
            .userModule(UserModule(this))
            .build().inject(this)

    }
}