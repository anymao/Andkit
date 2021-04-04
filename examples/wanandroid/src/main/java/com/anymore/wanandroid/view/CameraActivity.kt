package com.anymore.wanandroid.view

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.di.DataBundle
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.lifecycle.coroutines.bg
import com.anymore.andkit.lifecycle.coroutines.launch
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.baike.BaikeFinder
import com.anymore.baike.bean.BaikeResult
import com.anymore.camera.CameraxFragment
import com.anymore.livedata.ext.setDiffValue
import com.anymore.tensorflow.api.TensorFlowDetector
import com.anymore.tensorflow.api.TensorFlowInterpreterDetector
import com.anymore.wanandroid.R
import com.anymore.wanandroid.common.ext.click
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.databinding.ActicityCameraBinding
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.URL_VALUE
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber
import java.io.File

/**
 * Created by anymore on 2020/11/15.
 */
@DataBundle(clazz = CameraActivity.DataBundle::class)
class CameraActivity : BindingActivity<ActicityCameraBinding>() {

    companion object {
        const val TF_MODEL_NAME = "detect.tflite"
        const val TF_LABEL_NAME = "labelmap.txt"
    }

    private lateinit var mFragment: CameraxFragment
    private val model by lazy { Model() }
    private val mTensorFlowDir by lazy {
        File(
            filesDir,
            "tf-lite-models"
        ).apply { FileUtils.createOrExistsDir(this) }
    }
    private val englishMap by lazy {
        mutableMapOf<String, String>().apply {
            put("mouse", "鼠标")
            put("tv", "电视")
            put("laptop", "笔记本")
            put("remote", "传真机")
            put("keyboard", "键盘")
            put("cell phone", "手机")
            put("microwave", "无线电")
        }
    }

    private val mTensorFlowDetector: TensorFlowDetector by lazy {
        TensorFlowInterpreterDetector.Builder(this).apply {
            path = "${mTensorFlowDir.absolutePath}${File.separator}"
            labelName = "labelmap.txt"
            modelName = "detect.tflite"
            inputSize = 416
            isQuantized = true
        }.build()
    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.acticity_camera

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mBinding.m = model
        model.keyWords.observe(this) {
            if (!it.isNullOrBlank()) {
                doBaiduBaike(it)
            }
        }
        copyTensorFlowModel {
            checkPermissions(Manifest.permission.CAMERA, refusedEvent = {
                toast("必须授予相机权限")
                true
            }) {
                mFragment = CameraxFragment().apply {
                    onCatchBitmapListener = {
                        doTensorFlowDetect(it)
                    }
                }
                mFragmentManager.beginTransaction().add(R.id.flContainer, mFragment)
                    .commitAllowingStateLoss()
            }
        }
        mBinding.tvResult.click {
            val url = model.url.value
            if (url?.isNotEmpty() == true) {
                ARouter.getInstance()
                    .build(BROWSE_URL)
                    .withString(URL_VALUE, url)
                    .navigation(this)
            }
        }
        GlobalScope.async { }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun copyTensorFlowModel(success: () -> Unit) {
        launch(onFailure = {
            toast("复制模型文件失败：${it.message}")
        }) {
            val hasModel = bg {
                val targetModel = File(mTensorFlowDir, TF_MODEL_NAME)
                return@bg if (!FileUtils.isFileExists(targetModel)) {
                    assets.open(TF_MODEL_NAME).use {
                        FileIOUtils.writeFileFromIS(targetModel, it)
                    }
                } else {
                    true
                }
            }
            val hasLabel = bg {
                val targetLabel = File(mTensorFlowDir, TF_LABEL_NAME)
                return@bg if (!FileUtils.isFileExists(targetLabel)) {
                    assets.open(TF_LABEL_NAME).use {
                        FileIOUtils.writeFileFromIS(targetLabel, it)
                    }
                } else {
                    true
                }
            }
            if (hasLabel && hasModel) {
                success()
            }
        }
    }

    private fun doTensorFlowDetect(bitmap: Bitmap) {
        launch {
            val result = bg {
                mTensorFlowDetector.detect(bitmap)
            }
            val first = result.firstOrNull { it.confidence ?: 0f > 0.5f }
            first?.let {
                val zhKeywords = englishMap[first.title?.toLowerCase()]
                if (zhKeywords != null) {
                    model.keyWords.setDiffValue(zhKeywords)
                }
            }
        }
    }

    private fun doBaiduBaike(keywords: String) {
        launch {
            model.result.setDiffValue(keywords)
            val result = bg { BaikeFinder.find(keywords).apply { Timber.d(this.toString()) } }
            if (result.resultCode == BaikeResult.SUCCESS) {
                model.result.setDiffValue(result.baikeContent.orEmpty())
            }
            model.url.setDiffValue(result.baikeUrl.orEmpty())
        }
    }

    class Model {
        val keyWords = MutableLiveData<String>()
        val result = MutableLiveData<String>()
        val url = MutableLiveData<String>()
    }

    @Parcelize
    data class DataBundle(val id: Long? = null) : Parcelable
}