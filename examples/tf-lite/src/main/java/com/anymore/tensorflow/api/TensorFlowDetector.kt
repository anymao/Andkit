package com.anymore.tensorflow.api

import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.anymore.tensorflow.bean.RecognitionResult

/**
 * Created by lym on 2020/11/12.
 */
interface TensorFlowDetector {
    @WorkerThread
    fun detect(bitmap: Bitmap):List<RecognitionResult>
    fun close()
}