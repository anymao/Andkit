package com.anymore.wanandroid.di.component

import com.anymore.andkit.lifecycle.di.module.ApplicationModule
import com.anymore.wanandroid.ArticlesApplication
import com.anymore.wanandroid.di.module.ArticlesModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by anymore on 2020/1/25.
 */
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, ArticlesModule::class])
interface ArticlesModuleComponent {
    fun inject(application: ArticlesApplication)
}