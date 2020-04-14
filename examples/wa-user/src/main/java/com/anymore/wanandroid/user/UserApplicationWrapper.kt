package com.anymore.wanandroid.user

import android.app.Application
import com.anymore.andkit.lifecycle.application.AbsApplicationWrapper
import timber.log.Timber

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class UserApplicationWrapper(application: Application) : AbsApplicationWrapper(application) {

    override fun attachBaseContext() {
        Timber.i("attachBaseContext")
    }

    override fun onCreate() {
        Timber.i("onCreate")
    }

    override fun onTerminate() {
        Timber.i("onTerminate")
    }

}