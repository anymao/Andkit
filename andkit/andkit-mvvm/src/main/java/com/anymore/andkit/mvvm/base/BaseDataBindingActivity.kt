package com.anymore.andkit.mvvm.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.core.base.BaseActivity

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseDataBindingActivity<VB : ViewDataBinding> : BaseActivity(), LayoutProvider,
    ViewModelRegister {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
    }

    override fun register(viewModel: BaseViewModel) {
        viewModel.toast.observe(this) {

        }
        viewModel.successToast.observe(this) {

        }
        viewModel.failedToast.observe(this) {

        }
        viewModel.loading.observe(this) {
            if (it) {
                showLoading(null)
            } else {
                hideLoading()
            }
        }
    }
}