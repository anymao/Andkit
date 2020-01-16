package com.anymore.andkit.mvvm

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * 没有关联Model层的简单ViewModel
 * Created by liuyuanmao on 2019/2/20.
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IViewModel {

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    /**
     * 当我们有一些界面都需要一些通用的[LiveData]去管理数据状态的时候，可以让[mLiveDataSet]来管理
     * 这些数据状态的[LiveData],典型用处就是界面弹出toast
     */
    private val mLiveDataSet = HashMap<String, LiveData<*>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        Timber.d("onDestroy")
        mCompositeDisposable.clear()
    }

    protected fun addToCompositeDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    protected fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().getString(resId)
    }

    fun <L : LiveData<*>> setEventIfAbsent(tag: String, liveData: L): L {
        var previous: LiveData<*>?
        synchronized(mLiveDataSet){
            previous = mLiveDataSet[tag]
            if (previous == null) {
                mLiveDataSet[tag] = liveData
            }
        }
        @Suppress("UNCHECKED_CAST")
        return previous as L? ?: liveData
    }

    fun <L:LiveData<*>> getLiveDataByTag(tag: String):L?{
        synchronized(mLiveDataSet){
            @Suppress("UNCHECKED_CAST")
            return mLiveDataSet[tag] as L?
        }
    }
}
