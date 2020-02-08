package com.anymore.andkit.lifecycle.activity

/**
 * Created by liuyuanmao on 2019/2/20.
 */
interface IActivity {
    //以下三个方法提供给ActivityWrapper使用，ActivityWrapper在Application.ActivityLifecycleCallbacks中被自动装载
    fun useFragment() = true
    fun useEventBus() = false
    fun injectable() = false
}