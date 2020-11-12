package com.anymore.tensorflow.api

import android.content.Context
import android.graphics.Bitmap
import com.anymore.tensorflow.bean.RecognitionResult
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.metadata.MetadataExtractor
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset

/**
 * Created by lym on 2020/11/12.
 */
class TensorFlowInterpreterDetector private constructor(
    val modelPath: String,
    val labelPath: String,
    val threadNumber: Int,
    val inputSize: Int,
    val cancelable: Boolean,
    val useNNAPI: Boolean,
    val useXNNPACK: Boolean,
    val isQuantized: Boolean
) : TensorFlowDetector {

    companion object {
        // Only return this many results.
        private const val NUM_DETECTIONS = 10

        // Float model
        private const val IMAGE_MEAN = 127.5f
        private const val IMAGE_STD = 127.5f
    }

    private val mLabels = mutableListOf<String>()
    private lateinit var mTfInterpreter: Interpreter
    private var mImageData: ByteBuffer
    private val mIntValues = IntArray(inputSize * inputSize)
    private val mOutputLocations:Array<Array<IntArray>> = arrayOf()

    init {
        val modelFileBuffer = loadModelFile(modelPath)
        val metaData = MetadataExtractor(modelFileBuffer)
        try {
            BufferedReader(
                InputStreamReader(
                    metaData.getAssociatedFile(labelPath),
                    Charset.defaultCharset()
                )
            ).use {
                mLabels.addAll(it.readLines())
            }

        } catch (e: Exception) {
            Timber.e(e, "Read Labels Error")
        }
        try {
            val options = Interpreter.Options()
                .setNumThreads(threadNumber)
                .setCancellable(cancelable)
                .setUseNNAPI(useNNAPI)
                .setUseXNNPACK(useXNNPACK)
            mTfInterpreter = Interpreter(modelFileBuffer, options)

        } catch (e: Exception) {
            Timber.e(e, "Create Interpreter Error")
        }
        val numBytesPerChannel = if (isQuantized) {
            1
        } else {
            4
        }
        mImageData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * numBytesPerChannel)
        mImageData.order(ByteOrder.nativeOrder())

    }


    private fun loadModelFile(path: String): MappedByteBuffer {
        val length = File(path).length()
        return FileInputStream(path).use {
            val channel = it.channel
            val fd = it.fd
            channel.map(FileChannel.MapMode.READ_ONLY, 0, length)
        }
    }


    override fun detect(bitmap: Bitmap): List<RecognitionResult> {
        bitmap.getPixels(mIntValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        mImageData.rewind()
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val value = mIntValues[i * inputSize + j]
                if (isQuantized) {
                    // Quantized model
                    mImageData.put((value shr 16 and 0xFF).toByte())
                    mImageData.put((value shr 8 and 0xFF).toByte())
                    mImageData.put((value and 0xFF).toByte())
                } else { // Float model
                    mImageData.putFloat(((value shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                    mImageData.putFloat(((value shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                    mImageData.putFloat(((value and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                }
            }
        }
    }

    override fun close() {
        mTfInterpreter.close()
    }


    class Builder(private val context: Context) {
        var modelPath: String = ""
        var labelPath: String = ""
        var threadNumber: Int = 4
        var inputSize: Int = 1
        var cancelable: Boolean = true
        var useNNAPI: Boolean = true
        var useXNNPACK: Boolean = true
        var isQuantized: Boolean = true


        fun build(): TensorFlowDetector {
            return TensorFlowInterpreterDetector(
                modelPath,
                labelPath,
                threadNumber,
                inputSize,
                cancelable,
                useNNAPI,
                useXNNPACK,
                isQuantized
            )
        }
    }
}