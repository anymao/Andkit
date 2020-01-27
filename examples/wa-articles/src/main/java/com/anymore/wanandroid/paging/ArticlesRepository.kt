package com.anymore.wanandroid.paging

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anymore.andkit.repository.repositoryComponent
import com.anymore.wanandroid.api.WanAndroidArticlesApi
import com.anymore.wanandroid.entry.Article
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.paging.Listing


/**
 * Created by liuyuanmao on 2019/4/19.
 */
class ArticlesRepository(private val application: Application) {

    /**
     *主页文章列表
     */
    fun getHomeArticlesListing(): Listing<Article> {
        val api = application.repositoryComponent.getRepository().obtainRetrofitService(
            WAN_ANDROID_KEY, WanAndroidArticlesApi::class.java
        )
        val provider = HomePageArticlesProvider(api)
        val factory = ArticlesSourceFactory(provider)
        val data = LivePagedListBuilder<Int, Article>(
            factory,
            PagedList.Config
                .Builder()
                .setPageSize(20)
                .setPrefetchDistance(20)
                .build()
        )
            .build()

        return Listing<Article>(
            pagedList = data,
            status = Transformations.switchMap(factory.source) {
                it.mStatus
            },
            retry = Transformations.switchMap(factory.source) {
                it.mRetry
            }
        )
    }

//    /**
//     * 获取某个知识体系下的文章列表
//     */
//    fun getKnowledgesArticlesListing(cid: Int): Listing<Article> {
//        val api = application.getRepositoryComponent().getRepository().obtainRetrofitService(KEY,WanAndroidKnowledgeApi::class.java)
//        val apiWrapper = KnowledgesArticlesApiWrapper(api, cid)
//        val factory = ArticlesSourceFactory(apiWrapper)
//        val data = LivePagedListBuilder<Int,Article>(factory,
//            PagedList.Config
//                .Builder()
//                .setPageSize(20)
//                .setPrefetchDistance(20)
//                .build())
//            .build()
//
//        return Listing<Article>(
//            pagedList = data,
//            status = Transformations.switchMap(factory.source) {
//                it.mStatus
//            },
//            retry = Transformations.switchMap(factory.source){
//                it.mRetry
//            }
//        )
//    }
//
//    /**
//     * 获取已收藏文章列表
//     */
//    fun getCollectedArticlesListing(): Listing<Article> {
//        val api = application.getRepositoryComponent().getRepository().obtainRetrofitService(KEY,WanAndroidCollectApi::class.java)
//        val apiWrapper = CollectedArticlesApiWrapper(api)
//        val factory = ArticlesSourceFactory(apiWrapper)
//        val data = LivePagedListBuilder<Int,Article>(factory,
//            PagedList.Config
//                .Builder()
//                .setPageSize(20)
//                .setPrefetchDistance(20)
//                .build())
//            .build()
//
//        return Listing<Article>(
//            pagedList = data,
//            status = Transformations.switchMap(factory.source) {
//                it.mStatus
//            },
//            retry = Transformations.switchMap(factory.source){
//                it.mRetry
//            }
//        )
//    }
}

