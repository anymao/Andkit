package com.anymore.andkit.common.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toTimeStamp(format: String = "yyyy-MM-dd"): Long {
    return tryOr(0L) {
        SimpleDateFormat(format).parse(this)?.time.orZero()
    }
}

val now get() = System.currentTimeMillis()

val timeZoneUtcOffset: Int
    get() {
        val tz = TimeZone.getDefault() ?: return -1
        var rawOffset = tz.rawOffset / 60_000
        if (tz.inDaylightTime(Date())) {//夏令时要加1小时
            rawOffset += 60
        }
        return rawOffset
    }