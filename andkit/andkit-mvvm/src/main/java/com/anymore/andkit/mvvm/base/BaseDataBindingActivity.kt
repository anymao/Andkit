package com.anymore.andkit.mvvm.base

import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.core.base.BaseActivity
import com.anymore.andkit.core.ktx.toast
import com.anymore.andkit.core.ktx.toastFailed
import com.anymore.andkit.core.ktx.toastSuccess

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseDataBindingActivity<VB : ViewDataBinding> : BaseActivity(), LayoutProvider,
    ViewModelRegister {

    protected lateinit var binding: VB

    @CallSuper
    override fun initView() {
        super.initView()
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
    }

    override fun register(viewModel: BaseViewModel) {
        viewModel.toast.observe(this) {
            toast(it)
        }
        viewModel.successToast.observe(this) {
            toastSuccess(it)
        }
        viewModel.failedToast.observe(this) {
            toastFailed(it)
        }
        viewModel.loading.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }
}