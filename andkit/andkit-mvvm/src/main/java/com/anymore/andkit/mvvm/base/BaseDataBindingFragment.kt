package com.anymore.andkit.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.core.base.BaseFragment

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseDataBindingFragment<VB : ViewDataBinding> : BaseFragment(), LayoutProvider,
    ViewModelRegister {

    protected lateinit var binding: VB

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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