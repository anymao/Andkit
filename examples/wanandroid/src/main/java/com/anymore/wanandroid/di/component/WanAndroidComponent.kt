package com.anymore.wanandroid.di.component

import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.andkit.dagger.scope.ApplicationScope
import com.anymore.wanandroid.WanAndroidApplication
import com.anymore.wanandroid.articles.di.module.ArticlesModule
import com.anymore.wanandroid.mine.di.module.MineModule
import com.anymore.wanandroid.user.di.module.UserModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by liuyuanmao on 2020/1/17.
 */
@ApplicationScope
@Component(
    modules = [
        UserModule::class,
        ArticlesModule::class,
        MineModule::class,
        ApplicationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface WanAndroidComponent {
    fun inject(wanAndroidApplication: WanAndroidApplication)
}