package com.anymore.wanandroid.user.di.module

import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.wanandroid.user.mvvm.view.LoginActivity
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