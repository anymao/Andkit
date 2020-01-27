package com.anymore.wanandroid.di.module

import com.anymore.andkit.mvvm.di.ViewModelFactoryModule
import dagger.Module

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module(
    includes = [
        ViewModelFactoryModule::class,
        LoginActivityModule::class,
        RegisterActivityModule::class
    ]
)
class UserModule