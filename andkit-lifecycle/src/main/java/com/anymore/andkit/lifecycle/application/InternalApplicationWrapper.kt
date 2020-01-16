package com.anymore.andkit.lifecycle.application

import android.app.Application
import androidx.multidex.MultiDex
import com.anymore.andkit.lifecycle.activity.ActivityLifecycle

/**
 * kit-lib内置实现的[AbsApplicationWrapper]
 * Created by liuyuanmao on 2019/3/11.
 */
internal class InternalApplicationWrapper(application: Application) :
    AbsApplicationWrapper(application) {

    private val mActivityLifecycle = ActivityLifecycle()

    override fun attachBaseContext() {
        MultiDex.install(application)
    }

    override fun onCreate() {
        mActivityLifecycle.install(application)
    }

    override fun onTerminate() {
        mActivityLifecycle.uninstall(application)
    }


}