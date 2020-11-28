package com.anymore.wanandroid.repository.download

import android.app.IntentService
import android.content.Intent
import com.anymore.andkit.lifecycle.common.EXTRA
import com.anymore.wanandroid.common.ContextProvider
import timber.log.Timber

/**
 * Created by anymore on 2020/11/21.
 */
class DownloadService : IntentService("${ContextProvider.appName}-下载服务") {
    override fun onHandleIntent(intent: Intent?) {
        try {
            intent?.let {
                val fileInfo = intent.getParcelableExtra<DownloadFileInfo>(EXTRA)
                if (fileInfo == null){
                    Timber.w("没有有效的DownloadFileInfo,不处理")
                    return
                }
                when(fileInfo.action){
                    AfterDownloadAction.NONE -> {Timber.w("CALL DownloadService But do Nothing!")}
                    AfterDownloadAction.OPEN -> {
                    }
                    AfterDownloadAction.REDOWNLOAD -> {

                    }
                }
            }
        }catch (e:Exception){
            Timber.e(e,"处理下载完成回调异常")
        }
    }
}