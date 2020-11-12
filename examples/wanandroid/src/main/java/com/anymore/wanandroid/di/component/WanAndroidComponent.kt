package com.anymore.wanandroid.di.component

import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.andkit.dagger.scope.ApplicationScope
import com.anymore.wanandroid.WanAndroidApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/17.
 */
@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface WanAndroidComponent {
    fun inject(wanAndroidApplication: WanAndroidApplication)
}