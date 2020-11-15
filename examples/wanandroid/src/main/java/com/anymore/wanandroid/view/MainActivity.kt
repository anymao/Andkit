package com.anymore.wanandroid.view

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.SystemClock
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.lifecycle.coroutines.bg
import com.anymore.andkit.lifecycle.coroutines.launch
import com.anymore.andkit.mvp.BaseActivity
import com.anymore.tensorflow.api.TensorFlowDetector
import com.anymore.tensorflow.api.TensorFlowInterpreterDetector
import com.anymore.wanandroid.R
import com.anymore.wanandroid.common.ext.click
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.repository.glide.GlideApp
import com.anymore.wanandroid.route.MAIN_PAGE
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.properties.Delegates

@Route(path = MAIN_PAGE)
class MainActivity : BaseActivity() {

    companion object {
        const val REQUEST_SELECT_IMAGE = 63
    }

    private val mTensorFlowDetector: TensorFlowDetector by lazy {
        TensorFlowInterpreterDetector.Builder(this).apply {
            path = getExternalFilesDir(null)!!.absolutePath+File.separator
            labelName = "labelmap.txt"
            modelName = "detect.tflite"
            inputSize = 300
            isQuantized = true
        }.build()
    }

    //利用kotlin属性委托->标准委托中的Delegates.observable实现点击两次返回桌面
    private var lastPressedTime: Long by Delegates.observable(0L) { _, oldValue, newValue ->
        if (newValue - oldValue < 2000) {
            super.onBackPressed()
            //finish() //二选一
        } else {
            toast("再按一次退出应用")
        }
    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        image.setOnClickListener {
            checkPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) {
                Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .maxSelectable(1)
                    .imageEngine(GlideEngine())
                    .forResult(REQUEST_SELECT_IMAGE)
            }
        }
        btnCamera.click {
            startActivity(Intent(this,CameraActivity::class.java))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                val path = Matisse.obtainPathResult(data)?.firstOrNull()
                if (path != null) {
                    GlideApp.with(this)
                        .load(path)
                        .into(image)
                    startDetect(path)
                }
            }
        }
    }

    private fun startDetect(path: String) {
        launch {
            val result = bg {
                val image = BitmapFactory.decodeFile(path)
                mTensorFlowDetector.detect(image)
            }
            tvResult.text = result.toString()
        }
    }

    override fun onBackPressed() {
        lastPressedTime = SystemClock.uptimeMillis()
    }

    override fun useFragment() = true
}
