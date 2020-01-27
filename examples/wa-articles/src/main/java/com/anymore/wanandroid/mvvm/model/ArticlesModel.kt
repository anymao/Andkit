package com.anymore.wanandroid.mvvm.model

import android.app.Application
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.api.WanAndroidHomePageApi
import com.anymore.wanandroid.entry.Banner
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/25.
 */
class ArticlesModel @Inject constructor(mApplication: Application) : BaseModel(mApplication) {

    /**
     * 获取首页轮播图
     */
    fun getHomePageBanners(): Observable<List<Banner>> {
        Timber.d("mRepositoryComponent:${mRepositoryComponent.hashCode()}")
        return mRepositoryComponent.getRepository()
            .obtainRetrofitService(WAN_ANDROID_KEY, WanAndroidHomePageApi::class.java)
            .getBanner()
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.errorCode == ResponseCode.OK && it.data != null){
                    Observable.just(it.data!!)
                }else{
                    Observable.error(WanAndroidException(it.errorMsg?:"获取首页Banner失败!"))
                }
            }
            .observeOn(AndroidSchedulers.mainThread())

    }
}