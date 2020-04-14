package com.anymore.wanandroid.mine.mvp.contract

import com.anymore.andkit.mvp.BaseContract
import com.anymore.wanandroid.mine.entry.Article
import com.anymore.wanandroid.repository.base.PagedData
import io.reactivex.Observable

/**
 * Created by anymore on 2020/2/4.
 */
interface CollectedArticlesContract {
    interface ICollectedArticlesView : BaseContract.IBaseView {
        fun showCollectedArticles(data: List<Article>, pageNum: Int, hasMore: Boolean = true)
        fun refreshOrLoadFailed(refresh: Boolean)
        fun remove(index: Int)
    }

    interface ICollectedArticlesPresenter : BaseContract.IBasePresenter {
        fun refreshCollectedArticles()
        fun loadCollectedArticlesByPage(page: Int)
        fun cancelCollectArticle(index: Int, id: Int)
    }

    interface ICollectedArticlesModel : BaseContract.IBaseModel {
        fun loadCollectedArticlesByPage(page: Int): Observable<PagedData<Article>>
        fun cancelCollectTheArticle(id: Int): Observable<Pair<Int, String>>
    }
}