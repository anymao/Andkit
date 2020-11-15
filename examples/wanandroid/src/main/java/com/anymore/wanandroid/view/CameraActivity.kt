package com.anymore.wanandroid.view

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.lifecycle.coroutines.bg
import com.anymore.andkit.lifecycle.coroutines.launch
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.andkit.mvvm.ext.setPostValue
import com.anymore.baike.BaikeFinder
import com.anymore.baike.bean.BaikeResult
import com.anymore.camera.CameraxFragment
import com.anymore.tensorflow.api.TensorFlowDetector
import com.anymore.tensorflow.api.TensorFlowInterpreterDetector
import com.anymore.wanandroid.R
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.databinding.ActicityCameraBinding
import timber.log.Timber
import java.io.File

/**
 * Created by anymore on 2020/11/15.
 */
class CameraActivity : BindingActivity<ActicityCameraBinding>() {

    private lateinit var mFragment: CameraxFragment
    private val model by lazy { Model() }

    private val englishMap by lazy { mutableMapOf<String,String>().apply {
        put("mouse","鼠标")
        put("tv","电视")
        put("laptop","笔记本")
        put("remote","传真机")
        put("keyboard","键盘")
        put("cell phone","手机")
        put("microwave","无线电")
    } }

    private val mTensorFlowDetector: TensorFlowDetector by lazy {
        TensorFlowInterpreterDetector.Builder(this).apply {
            path = getExternalFilesDir(null)!!.absolutePath + File.separator
            labelName = "labelmap.txt"
            modelName = "detect.tflite"
            inputSize = 300
            isQuantized = true
        }.build()
    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.acticity_camera

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mBinding.m = model
        model.keyWords.observe(this, {
            Timber.tag("CameraActivity").d("keyWords:$it")
            doBaiduBaike(it)
        })
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

    private fun doTensorFlowDetect(bitmap: Bitmap) {
        launch {
            val result = bg {
                mTensorFlowDetector.detect(bitmap)
            }
            val first = result.firstOrNull { it.confidence ?: 0f > 0.5f }
            first?.let {
                val zhKeywords = englishMap[first.title?.toLowerCase()]
                if (zhKeywords!= null){
                    model.keyWords.setPostValue(zhKeywords)
                }
            }
        }
    }

    private fun doBaiduBaike(keywords:String){
        launch {
            model.result.setPostValue(keywords)
            val result = bg { BaikeFinder.find(keywords) }
            if (result.resultCode == BaikeResult.SUCCESS){
                model.result.setPostValue(result.baikeContent)
            }
        }
    }

    class Model {
        val keyWords = MutableLiveData("")
        val result = MutableLiveData("")
    }

}