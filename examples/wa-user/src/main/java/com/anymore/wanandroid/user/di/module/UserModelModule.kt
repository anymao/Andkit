package com.anymore.wanandroid.user.di.module

import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.user.mvvm.model.UserModel
import dagger.Module
import dagger.Provides

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module
class UserModelModule {

    @Provides
    @ActivityScope
    fun provideUserModel(model: UserModel): BaseModel = model

}