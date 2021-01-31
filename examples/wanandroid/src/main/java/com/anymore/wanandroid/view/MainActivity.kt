package com.anymore.wanandroid.view

import android.Manifest
import android.os.Bundle
import android.os.SystemClock
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.andkit.mvp.BaseActivity
import com.anymore.wanandroid.R
import com.anymore.wanandroid.common.ext.click
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.repository.download.AndkitDownloader
import com.anymore.wanandroid.route.MAIN_PAGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

@Route(path = MAIN_PAGE)
class MainActivity : BaseActivity() {

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
        btnDownload.click {
            checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                AndkitDownloader.download("http://cdn.1or1.icu/image/device-2020-04-04-144550.gif")
            }
        }
    }


    override fun onBackPressed() {
        lastPressedTime = SystemClock.uptimeMillis()
    }

}
