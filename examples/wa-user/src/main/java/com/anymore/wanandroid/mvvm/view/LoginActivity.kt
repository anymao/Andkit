package com.anymore.wanandroid.mvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.mvvm.BaseActivity
import com.anymore.wanandroid.common.event.LoadState
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.common.widget.LoadingDialog
import com.anymore.wanandroid.mvvm.viewmodel.LoginViewModel
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivityLoginBinding

/**
 * Created by liuyuanmao on 2019/3/29.
 */
@Route(path = "/user/login")
class LoginActivity : BaseActivity<WuActivityLoginBinding, LoginViewModel>() {

    private val mLoadingDialog by lazy { LoadingDialog(this) }

    override fun initView(savedInstanceState: Bundle?) = R.layout.wu_activity_login

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(mBinding.toolbar)
        mViewModel.toastEvent.observe(this, Observer { toast(it, Toast.LENGTH_LONG) })
        mViewModel.mLoadStateEvent.observe(this, Observer{
            when(it.state){
                LoadState.START->mLoadingDialog.apply {
                    title = it.message
                    show()
                }
                LoadState.COMPLETED->mLoadingDialog.dismiss()
            }
        })
        mViewModel.mLoginEvent.observe(this, Observer {
            Toast.makeText(this,"登陆成功!",Toast.LENGTH_LONG).show()
//            ActivityStackManager.finishUntil(MainActivity::class.java.name)
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        })
        mBinding.btnLogin.setOnClickListener {
            mViewModel.login(
                mBinding.etUserName.text.toString(),
                mBinding.etPwd.text.toString()
            )
        }
    }
}