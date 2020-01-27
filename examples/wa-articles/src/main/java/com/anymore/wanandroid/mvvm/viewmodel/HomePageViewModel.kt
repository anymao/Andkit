package com.anymore.wanandroid.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.anymore.andkit.lifecycle.scope.FragmentScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.andkit.mvvm.SingleLiveEvent
import com.anymore.wanandroid.entry.Banner
import com.anymore.wanandroid.mvvm.model.ArticlesModel
import com.anymore.wanandroid.paging.ArticlesRepository
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class HomePageViewModel @Inject constructor(application: Application,private val mModel:ArticlesModel) :
    BaseViewModel(application) {

    val mBanners by lazy { MutableLiveData<List<Banner>>() }
    val mErrorMessage by lazy { SingleLiveEvent<CharSequence>() }

    val mArticleListing by lazy { ArticlesRepository(application).getHomeArticlesListing() }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        loadBanners()
    }

    private fun loadBanners() {
        val disposable = mModel.getHomePageBanners()
            .subscribeBy(
                onNext = { mBanners.value = it },
                onError = { Timber.e(it) }
            )
        addToCompositeDisposable(disposable)
    }

    fun retry() {
        val retry = mArticleListing.retry.value
        retry?.invoke()
    }

}