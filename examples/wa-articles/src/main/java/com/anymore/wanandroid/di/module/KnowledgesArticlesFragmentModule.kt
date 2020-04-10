package com.anymore.wanandroid.di.module

import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.wanandroid.mvvm.view.fragment.KnowledgesArticlesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anymore on 2020/1/27.
 */
@Module
abstract class KnowledgesArticlesFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [KnowledgesArticlesViewModelModule::class, ArticlesModelModule::class])
    abstract fun contributesKnowledgesArticlesFragment(): KnowledgesArticlesFragment
}