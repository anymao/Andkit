package com.anymore.wanandroid.repository.download

import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.OkDownloadProvider
import com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnSQLite

/**
 * Created by anymore on 2020/11/20.
 */
class OkDownloadContentProvider : OkDownloadProvider() {
    override fun onCreate(): Boolean {
        val downloader = OkDownload.Builder(context!!)
            .downloadStore(BreakpointStoreOnSQLite(context))
            .build()
        OkDownload.setSingletonInstance(downloader)
        return super.onCreate()
    }

}