package com.anymore.wanandroid.common.ext

import com.anymore.wanandroid.common.executors.AppExecutors
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
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

fun <T> Maybe<T>.network2Main():Maybe<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.disk2Main():Maybe<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.network2Main():Single<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.disk2Main():Single<T>{
    return subscribeOn(AppExecutors.networkScheduler)
        .observeOn(AndroidSchedulers.mainThread())
}