package com.anymore.andkit.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.lifecycle.activity.IActivity

/**
 * Created by liuyuanmao on 2019/2/20.
 */
abstract class BindingActivity<B:ViewDataBinding> :AppCompatActivity(),
    IActivity {
    protected lateinit var mBinding:B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,initView(savedInstanceState))
        mBinding.lifecycleOwner = this
        initData(savedInstanceState)
    }

    @LayoutRes
    abstract fun initView(savedInstanceState: Bundle?): Int

    open fun initData(savedInstanceState: Bundle?){}
}