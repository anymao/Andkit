package com.anymore.andkit.lifecycle.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.anymore.andkit.lifecycle.ActivityStackManager

/**
 * Created by liuyuanmao on 2019/2/20.
 */
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {


    fun install(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun uninstall(application: Application) {
        application.unregisterActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let {
            ActivityStackManager.add(it)
        }

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }


    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let { ActivityStackManager.remove(it) }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

}