package com.anymore.tensorflow.bean

import android.graphics.RectF
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by lym on 2020/11/12.
 */
data class RecognitionResult(
    val id: Int? = null,
    val title: String? = null,
    val confidence: Float? = 0f,
    val location: RectF? = null
)