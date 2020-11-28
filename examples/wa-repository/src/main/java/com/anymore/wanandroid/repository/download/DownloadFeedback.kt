package com.anymore.wanandroid.repository.download

/**
 * 下载反馈
 * Created by anymore on 2020/11/22.
 */
enum class DownloadFeedback {
    /**
     * toast提示
     */
    TOAST,

    /**
     * 通知提示
     */
    NOTIFICATION,

    /**
     * 无提示
     */
    NONE
}