package com.anymore.andkit.lifecycle.fragment

import com.anymore.andkit.lifecycle.ComponentLifecycle

/**
 * Created by liuyuanmao on 2019/2/23.
 */
interface IFragment : ComponentLifecycle {
    fun useEventBus() = false
    fun injectable() = false
}