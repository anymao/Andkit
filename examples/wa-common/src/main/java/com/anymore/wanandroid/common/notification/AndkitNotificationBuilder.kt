package com.anymore.wanandroid.common.notification

import android.app.Notification
import android.app.PendingIntent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.anymore.wanandroid.common.ContextProvider

class AndkitNotificationBuilder(
    var channelId: String,
    var defaults: Int = Notification.DEFAULT_LIGHTS,
    var ongoing: Boolean = false,
    var priority: Int = NotificationCompat.PRIORITY_HIGH,
    var contentTitle: String = ContextProvider.appName,
    var contentText: String = ContextProvider.appName,
    var smallIcon: Int = ContextProvider.appIconRes,
    var largeIcon: Bitmap? = BitmapFactory.decodeResource(
        ContextProvider.resources,
        ContextProvider.appIconRes
    ),
    var contentIntent: PendingIntent? = null,
    var ticker: String = ContextProvider.appName,
    var autoCancel: Boolean = false,
    var progressMax: Int = 0,
    var progress: Int = 0,
    var progressIndeterminate: Boolean = false,
    private var vibratePattern: LongArray = longArrayOf(0),
    private var soundUri: Uri? = null
) {
    fun build(): Notification =
        NotificationCompat.Builder(ContextProvider.getApplicationContext(), channelId)
            .setDefaults(defaults)
            .setOngoing(ongoing)
            .setPriority(priority)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIcon)
            .setContentIntent(contentIntent)
            .setTicker(ticker)
            .setAutoCancel(autoCancel)
            .setProgress(progressMax, progress, progressIndeterminate)
            .setVibrate(vibratePattern)
            .setSound(soundUri)
            .build()
}