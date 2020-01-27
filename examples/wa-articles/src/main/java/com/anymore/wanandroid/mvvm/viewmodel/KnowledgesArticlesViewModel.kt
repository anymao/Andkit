package com.anymore.wanandroid.mvvm.viewmodel

import android.app.Application
import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.wanandroid.entry.Article
import com.anymore.wanandroid.paging.ArticlesRepository
import com.anymore.wanandroid.repository.paging.Listing
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/4/30.
 */


@FragmentScope
class KnowledgesArticlesViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val repository by lazy { ArticlesRepository(application) }


    fun getKnowledgesArticlesListing(cid: Int): Listing<Article> {
        return repository.getKnowledgesArticlesListing(cid)
    }
}