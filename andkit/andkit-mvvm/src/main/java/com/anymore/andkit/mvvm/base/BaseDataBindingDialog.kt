package com.anymore.andkit.mvvm.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.mvvm.R

/**
 * Created by anymore on 2022/11/27.
 */
abstract class BaseDataBindingDialog<VB : ViewDataBinding>(
    context: Context,
    themeResId: Int = R.style.AndkitDialogTheme
) :
    Dialog(context, themeResId), LayoutProvider {

    protected lateinit var binding: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), null, false)
        setContentView(binding.root)
        initView()
        getData()
    }

    open fun initView() {

    }

    open fun getData() {

    }


}