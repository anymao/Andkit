package com.anymore.andkit.di.component

import com.anymore.andkit.KitApplication
import com.anymore.andkit.di.module.AppModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by liuyuanmao on 2020/1/16.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,AppModule::class])
interface KitAppComponent {
    fun inject(kitApplication: KitApplication)
}