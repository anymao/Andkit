package com.anymore.wanandroid.articles.mvvm.viewmodel

import android.app.Application
import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.wanandroid.articles.mvvm.model.ArticlesModel
import javax.inject.Inject

/**
 * Created by anymore on 2020/4/17.
 */
@ActivityScope
class ArticlesSearchViewModel @Inject constructor(
    application: Application,
    private val mModel: ArticlesModel
) :
    BaseViewModel(application) {

}