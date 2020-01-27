package com.anymore.wanandroid.di.component

import com.anymore.andkit.lifecycle.di.module.ApplicationModule
import com.anymore.wanandroid.WanAndroidApplication
import com.anymore.wanandroid.di.module.ArticlesModule
import com.anymore.wanandroid.di.module.UserModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/17.
 */
//@ApplicationScope
@Component(
    modules = [
        UserModule::class,
        ArticlesModule::class,
        ApplicationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface WanAndroidComponent {
    fun inject(wanAndroidApplication: WanAndroidApplication)
}