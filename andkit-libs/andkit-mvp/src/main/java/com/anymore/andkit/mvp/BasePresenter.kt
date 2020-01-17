package com.anymore.andkit.mvp

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 针对可能需要在Presenter层取一些仓储层的对象的需求，暂时没有支持直接采用属性注入到XXXPresenter
 * 如果确实需求在Presenter层调用OkHttp或者Gson对象的需求，使用[BasePresenter.application]的getRepositoryComponent()方法
 * 获取仓储层管理器RepositoryComponent可以提供全局(与Model层为同一个)的Gson,OkHttp等对象
 * Created by liuyuanmao on 2019/7/16.
 */
abstract class BasePresenter<V: BaseContract.IBaseView>(protected val application: Application, protected val mView:V):
    BaseContract.IBasePresenter{

    protected val mCompositeDisposable by lazy { CompositeDisposable() }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        super.onCreate()
    }

    protected fun addDisposable(disposable: Disposable){
        mCompositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

    protected fun getString(@StringRes stringId:Int):String=application.getString(stringId)
}