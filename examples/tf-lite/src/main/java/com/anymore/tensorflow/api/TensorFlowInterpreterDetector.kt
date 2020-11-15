package com.anymore.tensorflow.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Size
import com.anymore.tensorflow.bean.RecognitionResult
import com.anymore.tensorflow.image.ImageCompressor
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
import kotlin.math.min

/**
 * Created by lym on 2020/11/12.
 */
class TensorFlowInterpreterDetector private constructor(
    val path: String,
    val modelName: String,
    val labelName: String,
    val threadNumber: Int,
    val inputSize: Int,
    val useNNAPI: Boolean,
    val isQuantized: Boolean
) : TensorFlowDetector {

    companion object {
        private const val TAG = "TensorFlowInterpreterDetector"
        // Only return this many results.
        private const val NUM_DETECTIONS = 10

        // Float model
        private const val IMAGE_MEAN = 127.5f
        private const val IMAGE_STD = 127.5f
    }

    private val mLabels = mutableListOf<String>()
    private lateinit var mTfInterpreter: Interpreter
    private var mImageData: ByteBuffer
    private var  mIntValues : IntArray
    private lateinit var mOutputLocations:Array<Array<FloatArray>>
    private lateinit var mOutputClasses:Array<FloatArray>
    private lateinit var mOutputScores:Array<FloatArray>
    private lateinit var mNumDetections:FloatArray


    init {
        val modelFileBuffer = loadModelFile(path+modelName)
        val metaData = MetadataExtractor(modelFileBuffer)
        try {
            BufferedReader(
                InputStreamReader(
                    metaData.getAssociatedFile(labelName),
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
                .setUseNNAPI(useNNAPI)
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
        mIntValues = IntArray(inputSize*inputSize)
    }


    private fun loadModelFile(path: String): MappedByteBuffer {
        val length = File(path).length()
        return FileInputStream(path).use {
            val channel = it.channel
            channel.map(FileChannel.MapMode.READ_ONLY, 0, length)
        }
    }


    override fun detect(bitmap: Bitmap): List<RecognitionResult> {
        val scropBitmap = ImageCompressor.process(bitmap, Size(inputSize,inputSize))
//        scropBitmap.getPixels(mIntValues, 0, scropBitmap.width, 0, 0, scropBitmap.width, scropBitmap.height)
//        mImageData.rewind()
//        for (i in 0 until inputSize) {
//            for (j in 0 until inputSize) {
//                val value = mIntValues[i * inputSize + j]
//                if (isQuantized) {
//                    // Quantized model
//                    mImageData.put((value shr 16 and 0xFF).toByte())
//                    mImageData.put((value shr 8 and 0xFF).toByte())
//                    mImageData.put((value and 0xFF).toByte())
//                } else { // Float model
//                    mImageData.putFloat(((value shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    mImageData.putFloat(((value shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    mImageData.putFloat(((value and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                }
//            }
//        }
        mOutputLocations = Array(1){ Array(NUM_DETECTIONS){ FloatArray(4) } }
        mOutputClasses = Array(1){ FloatArray(NUM_DETECTIONS) }
        mOutputScores = Array(1){ FloatArray(NUM_DETECTIONS) }
        mNumDetections = FloatArray(1)
        val outputMap = mutableMapOf<Int, Any>().apply {
            put(0, mOutputLocations)
            put(1, mOutputClasses)
            put(2, mOutputScores)
            put(3, mNumDetections)
        }
        mTfInterpreter.runForMultipleInputsOutputs(arrayOf(scropBitmap.buffer), outputMap)
        val outputDetectNum = min(NUM_DETECTIONS, mNumDetections[0].toInt())
        val result = mutableListOf<RecognitionResult>()
        for (i in 0 until outputDetectNum){
            val location = RectF(
                (mOutputLocations[0][i][1] * inputSize),
                (mOutputLocations[0][i][0] * inputSize),
                (mOutputLocations[0][i][3] * inputSize),
                (mOutputLocations[0][i][2] * inputSize)
            )
            result.add(
                RecognitionResult(
                    id = i, title = mLabels[mOutputClasses[0][i].toInt()],confidence = mOutputScores[0][i],location = location
                )
            )
        }
        return result.also {
            Timber.v("TensorFlowInterpreterDetector  detect:===>")
            Timber.v(it.toString())
            Timber.v("TensorFlowInterpreterDetector  detect:<===")
        }
    }

    override fun close() {
        mTfInterpreter.close()
    }


    class Builder(private val context: Context) {
        var path:String = ""
        var modelName: String = ""
        var labelName: String = ""
        var threadNumber: Int = 4
        var inputSize: Int = 1
        var useNNAPI: Boolean = true
        var isQuantized: Boolean = true


        fun build(): TensorFlowDetector {
            return TensorFlowInterpreterDetector(
                path,
                modelName,
                labelName,
                threadNumber,
                inputSize,
                useNNAPI,
                isQuantized
            )
        }
    }
}