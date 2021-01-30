package com.anymore.tensorflow.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.LruCache
import android.util.Size
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import timber.log.Timber
import kotlin.math.min

/**
 * Created by lym on 2020/11/13.
 */
internal object ImageCompressor {

    private val mProcessors by lazy { LruCache<String,ImageProcessor>(64) }

    private fun createOrGetProcessor(bitmapSize:Size,inputSize: Size):ImageProcessor{
        val key = "ImageCompressor_${bitmapSize}_$inputSize"
        var target = mProcessors.get(key)
        val cropSize = min(bitmapSize.width,bitmapSize.height)
        if (target == null ){
            target = ImageProcessor.Builder()
                .add(ResizeWithCropOrPadOp(cropSize, cropSize))
                .add(ResizeOp(inputSize.height, inputSize.width, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(NormalizeOp(0f, 1f))
                .build()
            mProcessors.put(key,target)
        }
        return target
    }

    fun process(bitmap: Bitmap,inputSize: Size): TensorImage {
        val tfImage = TensorImage(DataType.FLOAT32).apply { load(bitmap) }
        Timber.d("src:w=${tfImage.width},h=${tfImage.height},target:w=${inputSize.width},h=${inputSize.height}")
        val processor = createOrGetProcessor(Size(bitmap.width,bitmap.height),inputSize)
        return processor.process(tfImage)
    }


    fun compress(bitmap: Bitmap, size: Int): Bitmap {
        if (bitmap.width * bitmap.height > size * size) {
            val resultBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(resultBitmap)
            canvas.drawBitmap(bitmap, getTransformationMatrix(bitmap.width,bitmap.height,size,size,0,false)!!,null)
            return resultBitmap
        } else {
            return bitmap
        }
    }


    fun getTransformationMatrix(
        srcWidth: Int,
        srcHeight: Int,
        dstWidth: Int,
        dstHeight: Int,
        applyRotation: Int,
        maintainAspectRatio: Boolean
    ): Matrix? {
        val matrix = Matrix()
        if (applyRotation != 0) {
            if (applyRotation % 90 != 0) {
                Timber.w("Rotation of %d % 90 != 0", applyRotation)
            }
            // Translate so center of image is at origin.
            matrix.postTranslate(-srcWidth / 2.0f, -srcHeight / 2.0f)
            // Rotate around origin.
            matrix.postRotate(applyRotation.toFloat())
        }
        // Account for the already applied rotation, if any, and then determine how
        // much scaling is needed for each axis.
        val transpose = (Math.abs(applyRotation) + 90) % 180 == 0
        val inWidth = if (transpose) srcHeight else srcWidth
        val inHeight = if (transpose) srcWidth else srcHeight
        // Apply scaling if necessary.
        if (inWidth != dstWidth || inHeight != dstHeight) {
            val scaleFactorX = dstWidth / inWidth.toFloat()
            val scaleFactorY = dstHeight / inHeight.toFloat()
            if (maintainAspectRatio) {
                // Scale by minimum factor so that dst is filled completely while
                // maintaining the aspect ratio. Some image may fall off the edge.
                val scaleFactor = Math.max(scaleFactorX, scaleFactorY)
                matrix.postScale(scaleFactor, scaleFactor)
            } else {
                // Scale exactly to fill dst from src.
                matrix.postScale(scaleFactorX, scaleFactorY)
            }
        }
        if (applyRotation != 0) {
            // Translate back from origin centered reference to destination frame.
            matrix.postTranslate(dstWidth / 2.0f, dstHeight / 2.0f)
        }
        return matrix
    }
}