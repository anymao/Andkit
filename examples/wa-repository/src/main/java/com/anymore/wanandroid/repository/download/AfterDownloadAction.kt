package com.anymore.wanandroid.repository.download

/**
 * 下载完成动作
 * Created by anymore on 2020/11/22.
 */
enum class AfterDownloadAction {
    /**
     * 什么都不做
     */
    NONE,

    /**
     * 打开
     */
    OPEN,

    /**
     * 重新下载
     */
    REDOWNLOAD,
}