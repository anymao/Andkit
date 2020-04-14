package com.anymore.wanandroid.user.di.module

import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.wanandroid.user.mvvm.view.RegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liuyuanmao on 2020/1/19.
 */
@Module
abstract class RegisterActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [RegisterViewModelModule::class, UserModelModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity
}