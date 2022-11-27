package com.anymore.andkit.mvvm.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.mvvm.R

/**
 * Created by anymore on 2022/11/27.
 */
abstract class BaseDataBindingAppCompatDialog<VB : ViewDataBinding>(
    context: Context?,
    theme: Int = R.style.AndkitAppCompatDialogTheme
) :
    AppCompatDialog(context, theme), LayoutProvider {

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