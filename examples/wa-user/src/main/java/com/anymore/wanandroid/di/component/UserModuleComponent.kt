package com.anymore.wanandroid.di.component

import com.anymore.andkit.lifecycle.di.module.ApplicationModule
import com.anymore.wanandroid.UserApplication
import com.anymore.wanandroid.di.module.UserModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, UserModule::class])
interface UserModuleComponent {
    fun inject(userApplication: UserApplication)
}