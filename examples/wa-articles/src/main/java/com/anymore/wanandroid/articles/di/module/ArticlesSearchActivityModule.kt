package com.anymore.wanandroid.articles.di.module

import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.wanandroid.articles.mvvm.view.activity.ArticlesSearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anymore on 2020/4/20.
 */
@Module
abstract class ArticlesSearchActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ArticlesSearchViewModelModule::class, ArticlesModelModule::class])
    abstract fun contributesArticlesSearchActivity(): ArticlesSearchActivity
}