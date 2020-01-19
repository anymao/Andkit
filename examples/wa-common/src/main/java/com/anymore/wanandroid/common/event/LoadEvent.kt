package com.anymore.wanandroid.common.event

/**
 * Created by liuyuanmao on 2020/1/15.
 */
enum class LoadState{
    START,
    COMPLETED
}
data class LoadEvent(val state: LoadState, val message:String="")