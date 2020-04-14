package com.anymore.wanandroid.articles.mvvm.viewmodel

import android.app.Application
import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.articles.paging.ArticlesRepository
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