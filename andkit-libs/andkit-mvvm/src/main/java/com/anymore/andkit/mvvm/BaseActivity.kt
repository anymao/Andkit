package com.anymore.andkit.mvvm

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/2/20.
 */
abstract class BaseActivity<B:ViewDataBinding,VM: BaseViewModel>:
    BindingActivity<B>(){
    protected lateinit var mViewModel: VM

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        mViewModel.let { lifecycle.removeObserver(it) }
        super.onDestroy()
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initViewModel(getClassGenericParams(index = 1))
    }

    /**
     * 初始化ViewModel操作，默认的Viewmodel周期与当前Activity一致
     */
    open fun initViewModel(clazz: Class<VM>) {
        mViewModel = ViewModelProvider(this,mViewModelFactory).get(clazz)
        mViewModel.let { lifecycle.addObserver(it) }
    }

    override fun injectable()=true
}