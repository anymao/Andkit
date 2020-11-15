package com.anymore.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.mvvm.BindingFragment
import com.anymore.camera.databinding.FragmentCamera2Binding
import timber.log.Timber

/**
 * Created by anymore on 2020/11/15.
 */
@Suppress("DEPRECATION")
open class Camera2Fragment : BindingFragment<FragmentCamera2Binding>(), SurfaceHolder.Callback {

    companion object {
        private val cameraOptions by lazy {
            SparseIntArray().apply {
                put(Surface.ROTATION_0, 90)
                put(Surface.ROTATION_90, 0)
                put(Surface.ROTATION_180, 270)
                put(Surface.ROTATION_270, 180)
            }
        }
    }

    private lateinit var mCameraManager: CameraManager
    private lateinit var mSurfaceHolder: SurfaceHolder
    private lateinit var mImageReader: ImageReader
    private val mMainHandler by lazy { Handler(Looper.getMainLooper()) }
    private val mCameraWorkThread by lazy { HandlerThread("andkit-camera2").apply { start() } }
    private val mCameraHandler by lazy { Handler(mCameraWorkThread.looper) }
    private var mCameraDevice: CameraDevice? = null
    var onCatchBitmapListener: ((Bitmap) -> Unit)? = null

    private val mStateCallback: CameraDevice.StateCallback by lazy {
        object :
            CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                mCameraDevice = camera
                startPreview(camera)
            }

            override fun onDisconnected(camera: CameraDevice) {
                camera.close()
                mCameraDevice = null
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Timber.e("Open Camera Error:$error")
            }

        }
    }


    override fun getLayoutRes() = R.layout.fragment_camera2

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mSurfaceHolder = mBinding.sfvPreview.holder
        mSurfaceHolder.setKeepScreenOn(true)
        mSurfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        initCamera()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        releaseCamera()
    }

    @SuppressLint("MissingPermission")
    private fun initCamera() {
        mImageReader = ImageReader.newInstance(
            mBinding.sfvPreview.width,
            mBinding.sfvPreview.height,
            ImageFormat.JPEG,
            1
        )
        mImageReader.setOnImageAvailableListener({
            try {
                val image = mImageReader.acquireLatestImage()
                val buffer = image.planes.firstOrNull()?.buffer
                Timber.e("Read Image buffer!")
                buffer?.let {
                    val bytes = ByteArray(buffer.remaining())
                    buffer.get(bytes)
                    Timber.e("Read Image buffer bytes!")
                    val bitmap: Bitmap? = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    Timber.e("Read Image bitmap")
                    bitmap?.apply {
                        onCatchBitmap(this)
                    }
                }
                image.close()
            } catch (e: Exception) {
                Timber.e(e, "Read Image ERROR!")
            }
        }, mMainHandler)
        mCameraManager = requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        checkPermissions(Manifest.permission.CAMERA) {
            try {
                val rearCamera = CameraCharacteristics.LENS_FACING_FRONT.toString()
                mCameraManager.openCamera(rearCamera, mStateCallback, mCameraHandler)
            } catch (e: Exception) {
                Timber.e(e, "Open Camera ERROR!")
            }
        }
    }

    private fun releaseCamera() {
        mCameraDevice?.close()
        mCameraDevice = null
    }

    private fun startPreview(camera: CameraDevice) {
        try {
            val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            builder.addTarget(mSurfaceHolder.surface)

            camera.createCaptureSession(
                arrayListOf(mSurfaceHolder.surface, mImageReader.surface),
                object :
                    CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            builder.set(
                                CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                            )
                            val request = builder.build()
                            session.setRepeatingRequest(request, null, mCameraHandler)
                        } catch (e: Exception) {
                            Timber.e(e, "createCaptureSession onConfigured")
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Timber.e("createCaptureSession onConfigureFailed")
                    }
                },
                mCameraHandler
            )
        } catch (e: Exception) {
            Timber.e(e, "startPreview Error!")
        }
    }

    open fun onCatchBitmap(bitmap: Bitmap) {
        onCatchBitmapListener?.invoke(bitmap)
    }


}