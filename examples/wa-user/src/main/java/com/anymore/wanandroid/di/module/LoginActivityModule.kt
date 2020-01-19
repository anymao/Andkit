package com.anymore.wanandroid.di.module

import com.anymore.andkit.lifecycle.scope.ActivityScope
import com.anymore.wanandroid.mvvm.view.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module
abstract class LoginActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginViewModelModule::class, UserModelModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

}