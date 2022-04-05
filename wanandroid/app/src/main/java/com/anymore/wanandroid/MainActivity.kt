package com.anymore.wanandroid

import android.os.Bundle
import com.anymore.andkit.common.ktx.bg
import com.anymore.andkit.core.ktx.launchWithLoading
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.wanandroid.databinding.ActivityMainBinding
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {

    @Inject
    lateinit var service: WanAndroidService

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchWithLoading {
            delay(5000L)
            val vo = bg { service.getHomeBanners() }
            Timber.d(vo.toString())
        }
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        super.onDestroy()
    }
}