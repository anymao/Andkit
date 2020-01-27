package com.anymore.wanandroid.di.module

import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.mvvm.model.ArticlesModel
import dagger.Module
import dagger.Provides

/**
 * Created by anymore on 2020/1/25.
 */
@Module
class ArticlesModelModule {

    @Provides
    @FragmentScope
    fun provideArticlesModel(model: ArticlesModel): BaseModel = model
}