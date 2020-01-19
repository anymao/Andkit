package com.anymore.wanandroid.di.module

import com.anymore.andkit.lifecycle.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.mvvm.model.UserModel
import dagger.Module
import dagger.Provides

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module
class UserModelModule {

    @Provides
    @ActivityScope
    fun provideUserModel(model: UserModel): BaseModel =model

}