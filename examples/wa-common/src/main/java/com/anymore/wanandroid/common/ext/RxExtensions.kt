package com.anymore.wanandroid.common.ext

import com.anymore.wanandroid.common.executors.AppExecutors
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by anymore on 2020/2/1.
 */

fun <T> Observable<T>.network2Main():Observable<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.disk2Main():Observable<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}