package com.anymore.wanandroid.login.view

import android.app.Application
import com.anymore.andkit.common.ktx.bg
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.frame.router.WanAndroidRouter.register
import com.anymore.wanandroid.login.R
import com.anymore.wanandroid.login.databinding.LoginActivityRegisterBinding
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import com.didi.drouter.annotation.Router
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by anymore on 2022/4/6.
 */
@Router(scheme = naScheme, host = naHost, path = register)
@AndroidEntryPoint
class RegisterActivity : BaseDataBindingActivity<LoginActivityRegisterBinding>() {

    override fun getLayoutRes() = R.layout.login_activity_register


    @HiltViewModel
    class ViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
        @Inject
        lateinit var service: WanAndroidService

        suspend fun login(username: String, password: String) =
            bg { service.login(username, password) }
    }
}