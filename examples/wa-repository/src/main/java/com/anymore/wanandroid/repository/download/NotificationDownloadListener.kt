package com.anymore.wanandroid.repository.download

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.anymore.andkit.lifecycle.common.EXTRA
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.common.executors.AppExecutors
import com.anymore.wanandroid.common.notification.AndkitNotificationBuilder
import com.anymore.wanandroid.repository.R
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.SpeedCalculator
import com.liulishuo.okdownload.core.breakpoint.BlockInfo
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend
import timber.log.Timber

/**
 * Created by anymore on 2020/11/21.
 */
class NotificationDownloadListener(
    private val successActionIntent: PendingIntent? = null,
    private val failedActionIntent: PendingIntent? = null,
    val onDownLoadSuccess: ((DownloadTask) -> Boolean)? = null
) :
    DownloadListener4WithSpeed() {
    companion object {
        private val DownloadNotificationChannelId by lazy { "${ContextProvider.getApplicationContext().applicationInfo.packageName}-download" }
        private val DownloadNotificationChannelName by lazy {
            "${
                ContextProvider.getApplicationContext().getString(
                    R.string.app_name
                )
            }下载"
        }
    }

    private val mContext by lazy { ContextProvider.getApplicationContext() }
    private val mNotificationManager by lazy { mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private var mNotificationBuilder: AndkitNotificationBuilder
    private var mTotalDownloadSize: Int = 0

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DownloadNotificationChannelId,
                DownloadNotificationChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(false)
            channel.enableLights(false)
            channel.vibrationPattern = longArrayOf(0)
            channel.setSound(null, null)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationBuilder = AndkitNotificationBuilder(
            channelId = DownloadNotificationChannelId,
            defaults = Notification.DEFAULT_LIGHTS,
            ongoing = true,
            priority = NotificationCompat.PRIORITY_HIGH,
            contentTitle = ContextProvider.appName,
            contentText = "准备下载...",
            smallIcon = ContextProvider.appIconRes
        )
    }

    override fun taskStart(task: DownloadTask) {
        Timber.v("task:$task start!")
        mNotificationBuilder.ticker = "开始"
        mNotificationBuilder.ongoing = true
        mNotificationBuilder.autoCancel = false
        mNotificationBuilder.contentText = "开始下载..."
        mNotificationBuilder.progressMax = 0
        mNotificationBuilder.progress = 0
        mNotificationBuilder.progressIndeterminate = true
        mNotificationManager.notify(task.id, mNotificationBuilder.build())
    }

    override fun connectStart(
        task: DownloadTask,
        blockIndex: Int,
        requestHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Timber.v("task:$task connectStart!")
        mNotificationBuilder.ticker = "开始连接"
        mNotificationBuilder.contentText = "连接资源..."
        mNotificationBuilder.progressMax = 0
        mNotificationBuilder.progress = 0
        mNotificationBuilder.progressIndeterminate = true
        mNotificationManager.notify(task.id, mNotificationBuilder.build())
    }

    override fun connectEnd(
        task: DownloadTask,
        blockIndex: Int,
        responseCode: Int,
        responseHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Timber.v("task:$task connectEnd!")
        mNotificationBuilder.ticker = "连接完成"
        mNotificationBuilder.contentText = "资源已连接，正在下载..."
        mNotificationBuilder.progressMax = 0
        mNotificationBuilder.progress = 0
        mNotificationBuilder.progressIndeterminate = true
        mNotificationManager.notify(task.id, mNotificationBuilder.build())
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
        mNotificationBuilder.ongoing = false
        mNotificationBuilder.autoCancel = true
        val info: DownloadFileInfo
        if (cause == EndCause.COMPLETED) {
            if (onDownLoadSuccess?.invoke(task) != true) {
                info = DownloadFileInfo(
                    task.file?.absolutePath,
                    task.url, cause, AfterDownloadAction.OPEN, task.filename
                )
                mNotificationBuilder.progressMax = 1
                mNotificationBuilder.progress = 1
                mNotificationBuilder.progressIndeterminate = false
                mNotificationBuilder.ticker = "下载结束"
                mNotificationBuilder.contentText = "下载结束，平均速度: " + taskSpeed.averageSpeed()
                mNotificationBuilder.contentIntent =
                    successActionIntent ?: getDownloadServiceIntent(info)
                AppExecutors.mainHandler.postDelayed({
                    mNotificationManager.notify(task.id, mNotificationBuilder.build())
                }, 1000)
            }else{
                mNotificationManager.cancel(task.id)
            }
        } else {
            info = DownloadFileInfo(
                task.file?.absolutePath,
                task.url, cause, AfterDownloadAction.REDOWNLOAD, task.filename
            )
            mNotificationBuilder.progressIndeterminate = false
            mNotificationBuilder.ticker = "下载失败"
            mNotificationBuilder.contentText = "下载失败，请检查网络，点击重试"
            mNotificationBuilder.contentIntent =
                failedActionIntent ?: getDownloadServiceIntent(info)
            AppExecutors.mainHandler.postDelayed({
                mNotificationManager.notify(task.id, mNotificationBuilder.build())
            }, 1000)
        }
    }

    override fun infoReady(
        task: DownloadTask,
        info: BreakpointInfo,
        fromBreakpoint: Boolean,
        model: Listener4SpeedAssistExtend.Listener4SpeedModel
    ) {
        Timber.v("task:$task infoReady!>>>>>>>>>>>")
        Timber.v("info:$info,fromBreakpoint:$fromBreakpoint,model:$model")
        Timber.v("task:$task infoReady!<<<<<<<<<<<")
        if (fromBreakpoint) {
            mNotificationBuilder.ticker = "断点开始"
        } else {
            mNotificationBuilder.ticker = "开始"
        }
        mNotificationBuilder.contentText = "本次下载从断点[$fromBreakpoint]开始"
        mNotificationBuilder.progressMax = info.totalLength.toInt().apply {
            mTotalDownloadSize = this
        }
        mNotificationBuilder.progress = info.totalOffset.toInt()
        mNotificationBuilder.progressIndeterminate = true
        mNotificationManager.notify(task.id, mNotificationBuilder.build())
    }

    override fun progressBlock(
        task: DownloadTask,
        blockIndex: Int,
        currentBlockOffset: Long,
        blockSpeed: SpeedCalculator
    ) {
        Timber.v("task:$task progressBlock!>>>>>>>>>>>")
        Timber.v("blockIndex:$blockIndex,currentBlockOffset:$currentBlockOffset,blockSpeed:${blockSpeed.lastSpeed()}")
        Timber.v("task:$task progressBlock!<<<<<<<<<<<")
    }

    override fun progress(task: DownloadTask, currentOffset: Long, taskSpeed: SpeedCalculator) {
        mNotificationBuilder.contentText = "下载速度: " + taskSpeed.speed()
        mNotificationBuilder.progressMax = mTotalDownloadSize
        mNotificationBuilder.progress = currentOffset.toInt()
        mNotificationBuilder.progressIndeterminate = false
        mNotificationManager.notify(task.id, mNotificationBuilder.build())
    }

    override fun blockEnd(
        task: DownloadTask,
        blockIndex: Int,
        info: BlockInfo?,
        blockSpeed: SpeedCalculator
    ) {
        Timber.v("task:$task blockEnd!>>>>>>>>>>>")
        Timber.v("blockIndex:$blockIndex,info:$info,blockSpeed:${blockSpeed.lastSpeed()}")
        Timber.v("task:$task blockEnd!<<<<<<<<<<<")
    }

    private fun getDownloadServiceIntent(info: DownloadFileInfo): PendingIntent {
        return PendingIntent.getService(
            mContext,
            0,
            Intent(mContext, DownloadService::class.java).apply {
                putExtra(EXTRA, info)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}