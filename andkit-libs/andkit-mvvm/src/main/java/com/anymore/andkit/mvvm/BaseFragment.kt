package com.anymore.andkit.mvvm

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/2/23.
 */
abstract class BaseFragment<BD : ViewDataBinding, VM : BaseViewModel> :
    BindingFragment<BD>() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    protected lateinit var mViewModel: VM


    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        Timber.d("initData")
        initViewModel(getClassGenericParams(index = 1))
    }


    /**
     * 初始化ViewModel操作，默认的Viewmodel周期与当前Fragment一致,
     * 如果想让当前ViewModel的周期与其Activity一致，需要传入其容器Activity而非本身
     * <p>
     *     <code>
     *          override fun initViewModel(clazz: Class<TodoTabActivityViewModel>) {
     *               mViewModel = ViewModelProviders.of(activity!!,mViewModelFactory).get(clazz)
     *               mViewModel.let { lifecycle.addObserver(it) }
     *          }
     *     </code>
     */
    open fun initViewModel(clazz: Class<VM>) {
        mViewModel = ViewModelProvider(provideViewModelStoreOwner(), mViewModelFactory).get(clazz)
        mViewModel.let { lifecycle.addObserver(it) }
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        lifecycle.removeObserver(mViewModel)
        super.onDestroyView()
    }

    /**
     * 从哪个作用域来获取ViewModel实例，Fragment是比较特殊的，当存在ViewModel共享的时候，
     * 需要通过[ViewModelStoreOwner]来指定从哪个局域内获取的，默认[BaseFragment]获取的
     * ViewModel的作用域是与自身一致，但是如果需要ViewModel共享就指定此方法返回[requireActivity]
     */
    protected open fun provideViewModelStoreOwner(): ViewModelStoreOwner = this

    override fun injectable() = true
}