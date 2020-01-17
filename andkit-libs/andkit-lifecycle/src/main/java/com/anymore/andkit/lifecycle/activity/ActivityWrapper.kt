package com.anymore.andkit.lifecycle.activity

import android.app.Activity
import android.os.Bundle
import dagger.android.AndroidInjection
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * Created by liuyuanmao on 2019/2/20.
 */
class ActivityWrapper(private var mActivity: Activity, private var iActivity: IActivity) :
    IActivityLifecycle {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        if (iActivity.useEventBus()) {
            EventBus.getDefault().register(mActivity)
        }
        if (iActivity.injectable()) {
            Timber.d("need inject ,do AndroidInjection.inject(mActivity)")
            AndroidInjection.inject(mActivity)
        }
    }

    override fun onDestroy() {
        Timber.i("onDestroy")
        if (iActivity.useEventBus()) {
            EventBus.getDefault().unregister(mActivity)
        }
    }
}