package com.anymore.wanandroid.user.di.component

import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.user.UserApplication
import com.anymore.wanandroid.user.di.module.UserModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, UserModule::class])
interface UserModuleComponent {
    fun inject(userApplication: UserApplication)
}