package com.anymore.wanandroid.articles.di.component

import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.articles.ArticlesApplication
import com.anymore.wanandroid.articles.di.module.ArticlesModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by anymore on 2020/1/25.
 */
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, ArticlesModule::class])
interface ArticlesModuleComponent {
    fun inject(application: ArticlesApplication)
}