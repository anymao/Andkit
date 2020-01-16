package com.anymore.andkit.lifecycle.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.anymore.andkit.lifecycle.ActivityStackManager
import com.anymore.andkit.lifecycle.fragment.FragmentLifecycle

/**
 * Created by liuyuanmao on 2019/2/20.
 */
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {

    private val mActivityWrapperMap: HashMap<Activity, ActivityWrapper> = HashMap()
    private val mFragmentLifecycle by lazy { FragmentLifecycle() }

    fun install(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun uninstall(application: Application) {
        application.unregisterActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity is IActivity) {
            val activityWrapper = ActivityWrapper(activity, activity)
            activityWrapper.onCreate(savedInstanceState)
            mActivityWrapperMap[activity] = activityWrapper
        }
        activity?.let {
            ActivityStackManager.add(it)
            registerFragmentLifecycle(it)
        }

    }

    override fun onActivityStarted(activity: Activity?) {
        mActivityWrapperMap[activity]?.onStart()
    }

    override fun onActivityResumed(activity: Activity?) {
        mActivityWrapperMap[activity]?.onResume()
    }

    override fun onActivityPaused(activity: Activity?) {
        mActivityWrapperMap[activity]?.onPause()
    }

    override fun onActivityStopped(activity: Activity?) {
        mActivityWrapperMap[activity]?.onStop()
    }

    override fun onActivityDestroyed(activity: Activity?) {
        mActivityWrapperMap[activity]?.let {
            it.onDestroy()
            mActivityWrapperMap.remove(activity)
        }
        val useFragment = activity !is IActivity || activity.useFragment()
        if (activity is FragmentActivity && useFragment) {
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(mFragmentLifecycle)
        }
        activity?.let { ActivityStackManager.remove(it) }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        mActivityWrapperMap[activity]?.onSaveInstanceState(outState)
    }

    private fun registerFragmentLifecycle(activity: Activity) {
        // userFragment为true的情况有两种，一是activity没有继承IActivity接口，一是activity继承了IActivity接口并且useFragment()方法返回true.
        val useFragment = activity !is IActivity || activity.useFragment()
        if (activity is FragmentActivity && useFragment) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                mFragmentLifecycle,
                true
            )
        }
    }

}