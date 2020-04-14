package com.anymore.wanandroid.articles.di.module

import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.wanandroid.articles.mvvm.view.fragment.DiscoveryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anymore on 2020/1/27.
 */
@Module
abstract class DiscoveryFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [DiscoveryViewModelModule::class])
    abstract fun contributeDiscoveryFragment(): DiscoveryFragment
}