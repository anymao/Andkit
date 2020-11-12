package com.anymore.tensorflow.bean

import android.graphics.Rect
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by lym on 2020/11/12.
 */
@Parcelize
data class RecognitionResult(
    val id: String? = null,
    val title: String? = null,
    val confidence: Float? = 0f,
    val location: Rect? = null
) : Parcelable