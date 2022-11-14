package com.anymore.wanandroid.login.view

import android.app.Application
import com.anymore.andkit.common.ktx.bg
import com.anymore.andkit.common.ktx.click
import com.anymore.andkit.core.ktx.launchWithLoading
import com.anymore.andkit.core.ktx.toastFailed
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.andkit.mvvm.ktx.andkitViewModels
import com.anymore.wanandroid.frame.router.WanAndroidRouter.login
import com.anymore.wanandroid.frame.router.WanAndroidRouter.main
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.login.R
import com.anymore.wanandroid.login.databinding.LoginActivityLoginBinding
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import com.didi.drouter.annotation.Router
import com.didi.drouter.api.DRouter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by anymore on 2022/4/6.
 */
@Router(scheme = naScheme, host = naHost, path = login)
@AndroidEntryPoint
class LoginActivity : BaseDataBindingActivity<LoginActivityLoginBinding>() {

    private val vm by andkitViewModels<ViewModel>()

    override fun getLayoutRes() = R.layout.login_activity_login

    override fun initView() {
        super.initView()
        binding.btnLogin.click {
            doLogin()
        }
    }

    private fun doLogin() {
        val username = binding.tieUsername.text?.toString()
        if (username.isNullOrBlank()) {
            toastFailed("用户名不能为空")
            return
        }
        val password = binding.tiePassword.text?.toString()
        if (password.isNullOrBlank()) {
            toastFailed("密码不能为空")
            return
        }
        launchWithLoading {
            val vo = vm.login(username, password)
            if (vo != null) {
                DRouter.build(main).start(this@LoginActivity)
                finish()
            } else {
                toastFailed("登录失败")
            }
        }
    }


    @HiltViewModel
    class ViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
        @Inject
        lateinit var service: WanAndroidService

        suspend fun login(username: String, password: String) =
            bg { service.login(username, password) }
    }
}