package com.anymore.andkit.lifecycle.activity

import android.app.Activity
import android.os.Bundle

/**
 * Created by liuyuanmao on 2019/2/20.
 */
interface IActivityLifecycle {
    /**
     * 代理 [Activity.onCreate]
     *
     * @param savedInstanceState 数据恢复
     */
    fun onCreate(savedInstanceState: Bundle?)

    /**
     * 代理 [Activity.onStart]
     */
    fun onStart() {}

    /**
     * 代理 [Activity.onResume]
     */
    fun onResume() {}

    /**
     * 代理 [Activity.onPause]
     */
    fun onPause() {}

    /**
     * 代理 [Activity.onStop]
     */
    fun onStop() {}

    /**
     * 代理 [Activity.onSaveInstanceState]
     *
     * @param outState 数据保存
     */
    fun onSaveInstanceState(outState: Bundle?) {}

    /**
     * 代理 [Activity.onDestroy]
     */
    fun onDestroy()
}