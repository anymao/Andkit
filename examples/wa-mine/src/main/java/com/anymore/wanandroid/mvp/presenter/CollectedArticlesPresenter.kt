package com.anymore.wanandroid.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.common.ext.network2Main
import com.anymore.wanandroid.mvp.contract.CollectedArticlesContract
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by anymore on 2020/2/4.
 */
class CollectedArticlesPresenter @Inject constructor(
    application: Application,
    mView: CollectedArticlesContract.ICollectedArticlesView,
    private val mModel: CollectedArticlesContract.ICollectedArticlesModel
) : BasePresenter<CollectedArticlesContract.ICollectedArticlesView>(application, mView),
    CollectedArticlesContract.ICollectedArticlesPresenter {
    override fun refreshCollectedArticles() {
        val disposable = mModel.loadCollectedArticlesByPage(0)
            .network2Main()
            .subscribeBy(
                onNext = {
                    val currentPage = it.curPage
                    val totalPage = it.pageCount
                    val list = it.datas
                    val hasMore = currentPage < totalPage
                    mView.showCollectedArticles(list, currentPage, hasMore)
                },
                onError = {
                    mView.showError(it.message ?: "加载收藏列表失败！")
                }
            )
        addDisposable(disposable)
    }

    override fun loadCollectedArticlesByPage(page: Int) {
        val disposable = mModel.loadCollectedArticlesByPage(page)
            .network2Main()
            .subscribeBy(
                onNext = {
                    val currentPage = it.curPage
                    val totalPage = it.pageCount
                    val list = it.datas
                    val hasMore = currentPage < totalPage
                    mView.showCollectedArticles(list, currentPage, hasMore)
                },
                onError = {
                    mView.showError(it.message ?: "加载收藏列表失败！")
                }
            )
        addDisposable(disposable)
    }

    override fun cancelCollectArticle(index: Int, id: Int) {
        val disposable = mModel.cancelCollectTheArticle(id)
            .network2Main()
            .subscribeBy(
                onError = {
                    mView.showError("操作失败:${it.message}")
                },
                onNext = {
                    if (it.first == 0) {
                        mView.remove(index)
                        mView.showSuccess("操作成功")
                    } else {
                        mView.showError("操作失败:${it.second}")
                    }
                }
            )
        addDisposable(disposable)
    }

}