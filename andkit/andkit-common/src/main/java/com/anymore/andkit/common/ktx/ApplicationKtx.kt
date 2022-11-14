package com.anymore.andkit.common.ktx

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

val applicationInfo: ApplicationInfo by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    applicationContext.packageManager.getApplicationInfo(
        applicationContext.packageName,
        PackageManager.GET_META_DATA
    )
}

/**
 * 判断当前app是否处于允许debug状态
 */
val enableDebug by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { tryOr(false) { applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 } }