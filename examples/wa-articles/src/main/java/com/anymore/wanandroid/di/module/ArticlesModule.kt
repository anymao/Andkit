package com.anymore.wanandroid.di.module

import com.anymore.andkit.mvvm.di.ViewModelFactoryModule
import dagger.Module

/**
 * Created by anymore on 2020/1/25.
 */
@Module(
    includes = [
        ViewModelFactoryModule::class,
        HomePageFragmentModule::class,
        DiscoveryFragmentModule::class,
        KnowledgesArticlesFragmentModule::class
    ]
)
class ArticlesModule