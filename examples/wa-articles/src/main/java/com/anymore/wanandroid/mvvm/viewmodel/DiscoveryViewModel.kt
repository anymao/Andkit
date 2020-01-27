package com.anymore.wanandroid.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.anymore.andkit.dagger.scope.FragmentScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.andkit.mvvm.SingleLiveEvent
import com.anymore.wanandroid.entry.Knowledge
import com.anymore.wanandroid.mvvm.model.ArticlesModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@FragmentScope
class DiscoveryViewModel @Inject constructor(
    private val mModel: ArticlesModel,
    application: Application
) :
    BaseViewModel(application) {
    val mAllKnowledges = MutableLiveData<List<Knowledge>>()
    val mToast = SingleLiveEvent<CharSequence>()


    fun loadAllKnowledges() {
        val disposable = mModel.getAllKnowledges()
            .subscribeBy(onNext = {
                mAllKnowledges.value = it
            }, onError = {
                mToast.value = it.message
            })
        addToCompositeDisposable(disposable)
    }
}