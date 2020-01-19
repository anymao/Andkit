package com.anymore.wanandroid.mvvm.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.anymore.andkit.mvvm.BaseActivity
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.mvvm.viewmodel.RegisterViewModel
import com.anymore.wanandroid.user.R
import com.anymore.wanandroid.user.databinding.WuActivityRegisterBinding

/**
 * Created by liuyuanmao on 2019/3/20.
 */
class RegisterActivity : BaseActivity<WuActivityRegisterBinding, RegisterViewModel>() {

    override fun initView(savedInstanceState: Bundle?) = R.layout.wu_activity_register

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(mBinding.toolbar)
        mViewModel.mErrorMessage.observe(this, Observer { toast(it) })
        mViewModel.mMessage.observe(this, Observer { toast(it) })
        mBinding.btnRegister.setOnClickListener {
            mViewModel.register(
                mBinding.etUserName.text.toString(),
                mBinding.etPwd.text.toString(),
                mBinding.etRePwd.text.toString()
            )
        }
    }

}