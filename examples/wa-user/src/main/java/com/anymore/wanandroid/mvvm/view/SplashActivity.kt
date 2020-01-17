package com.anymore.wanandroid.mvvm.view

import android.os.Bundle
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivitySplashBinding

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class SplashActivity: BindingActivity<WuActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?)= R.layout.wu_activity_splash

}