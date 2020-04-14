package com.anymore.wanandroid.mine.di.component

import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.mine.MineApplication
import com.anymore.wanandroid.mine.di.module.MineModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by anymore on 2020/1/29.
 */
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        MineModule::class
    ]
)
interface MineComponent {
    fun inject(application: MineApplication)
}