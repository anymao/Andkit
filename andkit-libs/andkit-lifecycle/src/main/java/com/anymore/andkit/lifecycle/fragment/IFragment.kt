package com.anymore.andkit.lifecycle.fragment

import android.os.Bundle

/**
 * Created by liuyuanmao on 2019/2/23.
 */
interface IFragment {
    //以下三个方法提供给FragmentWrapper使用，FragmentWrapper在FragmentManager.FragmentLifecycleCallbacks中被自动装载
    fun initData(savedInstanceState: Bundle?){}
    fun useEventBus()=false
    fun injectable()=false
}