package com.anymore.wanandroid.di.module

import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.wanandroid.mvvm.view.fragment.HomePageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anymore on 2020/1/25.
 */

@Module
abstract class HomePageFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomePageViewModelModule::class, ArticlesModelModule::class])
    abstract fun contributeHomePageFragment(): HomePageFragment
}