package com.anymore.wanandroid.mvvm.view

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
import com.anymore.wanandroid.mvvm.viewmodel.RegisterViewModel
import com.anymore.wanandroid.route.MAIN_PAGE
import com.anymore.wanandroid.route.USER_REGISTER
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivityRegisterBinding



/**
 * Created by liuyuanmao on 2019/3/20.
 */
@Route(path = USER_REGISTER)
class RegisterActivity : BaseActivity<WuActivityRegisterBinding, RegisterViewModel>() {

    private val mLoadingDialog by lazy { LoadingDialog(this) }


    override fun initView(savedInstanceState: Bundle?) = R.layout.wu_activity_register

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(mBinding.toolbar)
        mViewModel.mErrorMessage.observe(this, Observer { toast(it) })
        mViewModel.mMessage.observe(this, Observer { toast(it) })
        mViewModel.mLoadStateEvent.observe(this, Observer {
            when (it.state) {
                LoadState.START -> mLoadingDialog.apply {
                    title = it.message
                    show()
                }
                LoadState.COMPLETED -> mLoadingDialog.dismiss()
            }
        })
        mViewModel.mRegisterEvent.observe(this, Observer {
            Toast.makeText(this, "注册成功!", Toast.LENGTH_LONG).show()
            ARouter.getInstance()
                .build(MAIN_PAGE)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation(this)
        })
        mBinding.btnRegister.setOnClickListener {
            mViewModel.register(
                mBinding.etUserName.text.toString(),
                mBinding.etPwd.text.toString(),
                mBinding.etRePwd.text.toString()
            )
        }
    }

}