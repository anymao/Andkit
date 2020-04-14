package com.anymore.wanandroid.user.mvvm.view

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.wanandroid.route.USER_LOGIN
import com.anymore.wanandroid.route.USER_REGISTER
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivitySplashBinding
import kotlinx.android.synthetic.main.wu_activity_splash.*

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class SplashActivity : BindingActivity<WuActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) = R.layout.wu_activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        btnLogin.setOnClickListener {
            ARouter.getInstance()
                .build(USER_LOGIN)
                .navigation(this)
        }
        btnRegister.setOnClickListener {
            ARouter.getInstance()
                .build(USER_REGISTER)
                .navigation(this)
        }
    }

}