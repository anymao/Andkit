package com.anymore.camera

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.annotation.WorkerThread
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.mvvm.BindingFragment
import com.anymore.camera.databinding.FragmentCameraxBinding
import com.anymore.camera.utils.YuvToRgbConverter
import timber.log.Timber
import java.util.concurrent.Executors

/**
 * Created by anymore on 2020/11/15.
 */
class CameraxFragment : BindingFragment<FragmentCameraxBinding>() {

    private val mMainExecutor by lazy { ContextCompat.getMainExecutor(requireContext()) }
    private lateinit var mPreview: Preview
    private lateinit var mCamera: Camera

    private val mMainHandler by lazy { Handler(Looper.getMainLooper()) }
    private val mCameraWorkThread by lazy { HandlerThread("andkit-camerax").apply { start() } }
    private val mCameraHandler by lazy { Handler(mCameraWorkThread.looper) }
    private val mAnalysisExecutor by lazy {
        Executors.newSingleThreadExecutor {
            Thread(it, "andkit-camerax-analysis-thread")
        }
    }
    private val mConverter by lazy { YuvToRgbConverter(requireContext()) }

    var onCatchBitmapListener: ((Bitmap) -> Unit)? = null

    override fun getLayoutRes() = R.layout.fragment_camerax

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        checkPermissions(Manifest.permission.CAMERA) {
            startCamera()
        }
    }


    @SuppressLint("UnsafeExperimentalUsageError")
    private fun startCamera() {
        val providerFuture = ProcessCameraProvider.getInstance(requireContext())
        providerFuture.addListener({
            val provider = providerFuture.get()
            mPreview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(mBinding.pvPreview.display.rotation)
                .build()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(mBinding.pvPreview.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                .build()
            imageAnalysis.setAnalyzer(mAnalysisExecutor,
                { image ->
                    try {
                        val bitmap =
                            Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
                        image.use {
                            mConverter.yuvToRgb(it.image!!, bitmap)
                            onCatchBitmapListener?.invoke(bitmap)
                        }

                    } catch (e: Exception) {
                        Timber.e(e, "analyze ERROR!")
                    }
                })
            val cameraTarget =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try {
                provider.unbindAll()
                mCamera = provider.bindToLifecycle(this, cameraTarget, mPreview,imageAnalysis)
                mPreview.setSurfaceProvider(mBinding.pvPreview.surfaceProvider)
            } catch (e: Exception) {
                Timber.e(e, "startCamera ERROR!")
            }
        }, mMainExecutor)
    }

}