package com.anymore.wanandroid.login.view

import android.app.Application
import com.anymore.andkit.common.ktx.map
import com.anymore.andkit.common.livedata.NullSafetyLiveData
import com.anymore.andkit.core.ktx.launch
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.andkit.mvvm.ktx.andkitViewModels
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.frame.router.WanAndroidRouter.splash
import com.anymore.wanandroid.login.R
import com.anymore.wanandroid.login.databinding.LoginActivitySplashBinding
import com.didi.drouter.annotation.Router
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by anymore on 2022/4/7.
 */
@Router(scheme = naScheme, host = naHost, path = splash)
@AndroidEntryPoint
class SplashActivity : BaseDataBindingActivity<LoginActivitySplashBinding>() {

    private val vm by andkitViewModels<ViewModel>()

    override fun getLayoutRes() = R.layout.login_activity_splash

    override fun initView() {
        super.initView()
        vm.start.observe(this) {
            if (it) {
//                startFlutter(path = "/login")

//                go("https://www.baidu.com")
            }
        }
    }

    override fun getData() {
        super.getData()
        launch {
            vm.startTimber()
        }
    }

    @HiltViewModel
    class ViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
        val timer = NullSafetyLiveData(5)
        val start = timer.map { it == 0 }
        suspend fun startTimber() {
            while (timer.value >= 0) {
                delay(1000)
                timer.value = timer.value - 1
            }
        }
    }
}