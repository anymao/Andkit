package com.anymore.wanandroid.repository.download

import android.os.Environment
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.common.ext.toast
import com.blankj.utilcode.util.FileUtils
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.SpeedCalculator
import com.liulishuo.okdownload.core.breakpoint.BlockInfo
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.listener.DownloadListener2
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend
import timber.log.Timber
import java.io.File

/**
 * Created by anymore on 2020/11/20.
 */
object AndkitDownloader {
    private val downloader by lazy { OkDownload.with() }
    private val context by lazy { ContextProvider.getApplicationContext() }
    var defaultDownloadDir =
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath ?: File(
            context.filesDir, Environment.DIRECTORY_DOWNLOADS
        ).apply { FileUtils.createOrExistsDir(this) }.absolutePath

    fun download(url: String, targetFileName: String? = null, storeDirectory: String? = null) {
        val task = DownloadTask.Builder(url, storeDirectory ?: defaultDownloadDir, targetFileName)
            .setPassIfAlreadyCompleted(true)
            .build()
        task.enqueue(NotificationDownloadListener(onDownLoadSuccess = {
            toast("文件已成功保存：${it.file?.absolutePath}")
            true
        }))
    }


    private val logListener = object : DownloadListener2() {
        override fun taskStart(task: DownloadTask) {
            Timber.v("task:$task start!")
        }

        override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
            Timber.v("task:$task end >>>>>>>>>>>")
            Timber.v("task end with:${cause.name}")
            realCause?.let {
                Timber.e(realCause, "task end with error!")
            }
            Timber.v("task:$task end <<<<<<<<<<<<")
        }

    }

    private val notificationListener = object : DownloadListener4WithSpeed() {
        override fun taskStart(task: DownloadTask) {
            Timber.v("task:$task start!")
        }

        override fun connectStart(
            task: DownloadTask,
            blockIndex: Int,
            requestHeaderFields: MutableMap<String, MutableList<String>>
        ) {
            Timber.v("task:$task connectStart!")

        }

        override fun connectEnd(
            task: DownloadTask,
            blockIndex: Int,
            responseCode: Int,
            responseHeaderFields: MutableMap<String, MutableList<String>>
        ) {
            Timber.v("task:$task connectEnd!")
        }

        override fun taskEnd(
            task: DownloadTask,
            cause: EndCause,
            realCause: java.lang.Exception?,
            taskSpeed: SpeedCalculator
        ) {
            Timber.v("task:$task end >>>>>>>>>>>")
            Timber.v("task end with:${cause.name}")
            realCause?.let {
                Timber.e(realCause, "task end with error!")
            }
            Timber.v("task:$task end <<<<<<<<<<<<")
        }

        override fun infoReady(
            task: DownloadTask,
            info: BreakpointInfo,
            fromBreakpoint: Boolean,
            model: Listener4SpeedAssistExtend.Listener4SpeedModel
        ) {
            Timber.v("task:$task infoReady!>>>>>>>>>>>")
            Timber.d("info:$info,fromBreakpoint:$fromBreakpoint,model:$model")
            Timber.v("task:$task infoReady!<<<<<<<<<<<")
        }

        override fun progressBlock(
            task: DownloadTask,
            blockIndex: Int,
            currentBlockOffset: Long,
            blockSpeed: SpeedCalculator
        ) {
            Timber.v("task:$task progressBlock!>>>>>>>>>>>")
            Timber.d("blockIndex:$blockIndex,currentBlockOffset:$currentBlockOffset,blockSpeed:${blockSpeed.lastSpeed()}")
            Timber.v("task:$task progressBlock!<<<<<<<<<<<")
        }

        override fun progress(task: DownloadTask, currentOffset: Long, taskSpeed: SpeedCalculator) {
            Timber.v("task:$task progress!>>>>>>>>>>>")
            Timber.d("currentOffset:$currentOffset,taskSpeed:${taskSpeed.lastSpeed()}")
            Timber.v("task:$task progress!<<<<<<<<<<<")
        }

        override fun blockEnd(
            task: DownloadTask,
            blockIndex: Int,
            info: BlockInfo?,
            blockSpeed: SpeedCalculator
        ) {
            Timber.v("task:$task blockEnd!>>>>>>>>>>>")
            Timber.d("blockIndex:$blockIndex,info:$info,blockSpeed:${blockSpeed.lastSpeed()}")
            Timber.v("task:$task blockEnd!<<<<<<<<<<<")
        }
    }
}