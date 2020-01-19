package com.anymore.wanandroid.di.component

import com.anymore.wanandroid.WanAndroidApplication
import com.anymore.wanandroid.di.module.WanAndroidModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/17.
 */
@Component(modules = [
    WanAndroidModule::class,
    AndroidSupportInjectionModule::class
]
)
interface WanAndroidComponent {
    fun inject(wanAndroidApplication: WanAndroidApplication)
}