package com.anymore.wanandroid.di.module

import android.app.Application
import com.anymore.andkit.mvvm.di.ViewModelFactoryModule
import dagger.Module
import dagger.Provides

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module(includes = [ViewModelFactoryModule::class, LoginActivityModule::class])
class UserModule(private val application: Application){

    @Provides
    fun provideApplication():Application = application

}