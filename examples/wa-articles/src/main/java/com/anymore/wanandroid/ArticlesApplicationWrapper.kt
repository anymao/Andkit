package com.anymore.wanandroid

import android.app.Application
import com.anymore.andkit.lifecycle.application.AbsApplicationWrapper
import timber.log.Timber

/**
 * Created by anymore on 2020/1/25.
 */
class ArticlesApplicationWrapper(application: Application) : AbsApplicationWrapper(application) {
    override fun attachBaseContext() {
        Timber.i("ArticlesApplicationWrapper===>attachBaseContext")
    }

    override fun onCreate() {
        Timber.i("ArticlesApplicationWrapper===>onCreate")
    }

    override fun onTerminate() {
        Timber.i("ArticlesApplicationWrapper===>onTerminate")
    }
}