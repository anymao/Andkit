package com.anymore.wanandroid.di.component

import com.anymore.andkit.lifecycle.scope.ApplicationScope
import com.anymore.wanandroid.UserApplication
import com.anymore.wanandroid.di.module.UserModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class,UserModule::class])
interface UserModuleComponent{
    fun inject(userApplication: UserApplication)
}