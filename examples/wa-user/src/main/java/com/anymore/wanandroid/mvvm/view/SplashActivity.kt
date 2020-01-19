package com.anymore.wanandroid.mvvm.view

import android.content.Intent
import android.os.Bundle
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivitySplashBinding
import kotlinx.android.synthetic.main.wu_activity_splash.*

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class SplashActivity: BindingActivity<WuActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?)= R.layout.wu_activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

}