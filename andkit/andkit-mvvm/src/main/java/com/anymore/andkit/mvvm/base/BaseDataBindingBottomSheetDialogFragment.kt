package com.anymore.andkit.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.core.base.BaseBottomSheetDialogFragment
import com.anymore.andkit.core.ktx.toast
import com.anymore.andkit.core.ktx.toastFailed
import com.anymore.andkit.core.ktx.toastSuccess

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseDataBindingBottomSheetDialogFragment<VB : ViewDataBinding> :
    BaseBottomSheetDialogFragment(), LayoutProvider,
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