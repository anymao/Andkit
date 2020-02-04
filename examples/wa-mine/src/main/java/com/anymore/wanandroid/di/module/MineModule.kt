package com.anymore.wanandroid.di.module

import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.wanandroid.mvp.view.activity.TodoActivity
import com.anymore.wanandroid.mvp.view.activity.TodoTabActivity
import com.anymore.wanandroid.mvp.view.fragment.CollectedArticlesFragment
import com.anymore.wanandroid.mvp.view.fragment.TodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anymore on 2020/1/29.
 */
@Module
abstract class MineModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [PresentersModule::class, ViewsModule::class, ModelsModule::class])
    abstract fun contributesTodoTabActivity(): TodoTabActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [PresentersModule::class,ViewsModule::class,ModelsModule::class])
    abstract fun contributesTodoListFragment():TodoListFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [PresentersModule::class,ViewsModule::class,ModelsModule::class])
    abstract fun contributesTodoActivity(): TodoActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [PresentersModule::class,ViewsModule::class,ModelsModule::class])
    abstract fun contributesCollectedArticlesFragment():CollectedArticlesFragment
}