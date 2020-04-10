package com.anymore.wanandroid.mvp.model

import android.app.Application
import com.anymore.andkit.mvp.BaseModel
import com.anymore.andkit.repository.repositoryComponent
import com.anymore.wanandroid.api.WanAndroidCollectApi
import com.anymore.wanandroid.entry.Article1
import com.anymore.wanandroid.mvp.contract.CollectedArticlesContract
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by anymore on 2020/2/4.
 */
class CollectedArticlesModel @Inject constructor(mApplication: Application) :
    BaseModel(mApplication), CollectedArticlesContract.ICollectedArticlesModel {

    private val repository by lazy { mApplication.repositoryComponent.getRepository() }
    private val api by lazy {
        repository.obtainRetrofitService(
            WAN_ANDROID_KEY,
            WanAndroidCollectApi::class.java
        )
    }

    override fun loadCollectedArticlesByPage(page: Int): Observable<PagedData<Article1>> {
        return api.getAllCollectedArticles(page)
            .map {
                if (it.errorCode == ResponseCode.OK && it.data != null) {
                    return@map it.data!!
                } else {
                    throw WanAndroidException("获取收藏文章失败:${it.errorMsg}")
                }
            }
    }

    override fun cancelCollectTheArticle(id: Int): Observable<Pair<Int, String>> {
        return api.uncollectWanAndroidArticle(id)
            .map {
                if (it.errorCode == ResponseCode.OK) {
                    return@map Pair(0, "操作成功")
                } else {
                    return@map Pair(1, "操作失败:${it.errorMsg}")
                }
            }
    }
}