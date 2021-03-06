package com.anymore.wanandroid.user.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseActivity
import com.anymore.wanandroid.common.event.LoadState
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.common.widget.LoadingDialog
import com.anymore.wanandroid.user.mvvm.viewmodel.LoginViewModel
import com.anymore.wanandroid.route.MAIN_PAGE
import com.anymore.wanandroid.route.USER_LOGIN
import com.anymore.wanandroid.route.USER_REGISTER
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivityLoginBinding

/**
 * Created by liuyuanmao on 2019/3/29.
 */
@Route(path = USER_LOGIN)
class LoginActivity : BaseActivity<WuActivityLoginBinding, LoginViewModel>() {

    private val mLoadingDialog by lazy { LoadingDialog(this) }

    override fun initView(savedInstanceState: Bundle?) = R.layout.wu_activity_login

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(mBinding.toolbar)
        mViewModel.toastEvent.observe(this, Observer { toast(it, Toast.LENGTH_LONG) })
        mViewModel.mLoadStateEvent.observe(this, Observer {
            when (it.state) {
                LoadState.START -> mLoadingDialog.apply {
                    title = it.message
                    show()
                }
                LoadState.COMPLETED -> mLoadingDialog.dismiss()
            }
        })
        mViewModel.mLoginEvent.observe(this, Observer {
            Toast.makeText(this, "登陆成功!", Toast.LENGTH_LONG).show()
            ARouter.getInstance()
                .build(MAIN_PAGE)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation(this)
        })
        mBinding.btnLogin.setOnClickListener {
            mViewModel.login(
                mBinding.etUserName.text.toString(),
                mBinding.etPwd.text.toString()
            )
        }
        mBinding.tvToRegister.setOnClickListener {
            ARouter.getInstance()
                .build(USER_REGISTER)
                .navigation(this)
        }
    }
}