package com.anymore.andkit

import android.app.Application
import androidx.multidex.MultiDex
import com.anymore.andkit.lifecycle.activity.ActivityLifecycle
import com.anymore.andkit.lifecycle.application.AbsApplicationWrapper
import timber.log.Timber

/**
 * kit-lib内置实现的[AbsApplicationWrapper]
 * Created by liuyuanmao on 2019/3/11.
 */
internal class InternalApplicationWrapper(application: Application) :
    AbsApplicationWrapper(application) {

    private val mActivityLifecycle = ActivityLifecycle()

    override fun attachBaseContext() {
        Timber.i("attachBaseContext")
        MultiDex.install(application)
    }

    override fun onCreate() {
        Timber.i("onCreate")
        mActivityLifecycle.install(application)
    }

    override fun onTerminate() {
        Timber.i("onTerminate")
        mActivityLifecycle.uninstall(application)
    }


}