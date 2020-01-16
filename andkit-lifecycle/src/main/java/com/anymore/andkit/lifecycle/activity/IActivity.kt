package com.anymore.andkit.lifecycle.activity

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * Created by liuyuanmao on 2019/2/20.
 */
interface IActivity {
    @LayoutRes
    fun initView(savedInstanceState: Bundle?): Int

    fun initData(savedInstanceState: Bundle?)
    //以下三个方法提供给ActivityWrapper使用，ActivityWrapper在Application.ActivityLifecycleCallbacks中被自动装载
    fun useFragment() = false

    fun useEventBus() = false
    fun injectable() = false
}