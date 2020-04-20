package com.anymore.wanandroid.articles.paging

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.common.executors.AppExecutors
import com.anymore.wanandroid.repository.base.NetStatus
import com.anymore.wanandroid.repository.paging.Retry
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.Executor

/**
 * DataSource除了要完成列表数据的请求，还要保存列表请求过程中的状态以及加载时出错的重试操作
 * Created by anymore on 2019/4/7.
 */
class ArticlesSource(private val provider: ArticlesProvider) : PageKeyedDataSource<Int, Article>() {

    val mRetry = MutableLiveData<Retry?>()
    val mStatus = MutableLiveData<NetStatus>()

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        Timber.d("loadInitial:")
        mStatus.postValue(NetStatus.DOING)
        mRetry.postValue(null)
        provider.loadInitial(page = 0)
            .subscribeBy(onNext = {
                val next = if (it.curPage < it.pageCount) {
                    it.curPage
                } else {
                    null
                }
                callback.onResult(it.datas, null, next)
                mRetry.postValue(null)
                mStatus.postValue(NetStatus.SUCCESS)
            }, onError = {
                mRetry.postValue {
                    getRetryExecutor().execute {
                        loadInitial(params, callback)
                    }
                }
                mStatus.postValue(NetStatus.failed(it))
            })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        Timber.d("loadAfter  call:${params.key}")
        mStatus.postValue(NetStatus.DOING)
        mRetry.postValue(null)
        provider.loadAfter(page = params.key)
            .subscribeBy(onNext = {
                val next = if (it.curPage < it.pageCount) {
                    it.curPage
                } else {
                    null
                }
                callback.onResult(it.datas, next)
                mRetry.postValue(null)
                mStatus.postValue(NetStatus.SUCCESS)
            }, onError = {
                mRetry.postValue {
                    getRetryExecutor().execute {
                        loadAfter(params, callback)
                    }
                }
                mStatus.postValue(NetStatus.failed(it))
            })
    }

    @SuppressLint("CheckResult")
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        Timber.d("loadBefore  call:${params.key}")
        mStatus.postValue(NetStatus.DOING)
        mRetry.postValue(null)
        provider.loadAfter(page = params.key)
            .subscribeBy(onNext = {
                val previous = if (it.curPage > 0) {
                    it.curPage - 1
                } else {
                    null
                }
                callback.onResult(it.datas, previous)
                mRetry.postValue(null)
                mStatus.postValue(NetStatus.SUCCESS)
            }, onError = {
                mRetry.postValue {
                    getRetryExecutor().execute {
                        loadBefore(params, callback)
                    }
                }
                mStatus.postValue(NetStatus.failed(it))
            })
    }

    //DataSource自己正常加载的时候，是运行在内部自己的线程池中的，
    // 但是重试的时候，线程池需要我们自己指定，不然默认在主线程执行，会造成NetworkOnMainThreadException
    private fun getRetryExecutor(): Executor = AppExecutors.networkExecutor

}