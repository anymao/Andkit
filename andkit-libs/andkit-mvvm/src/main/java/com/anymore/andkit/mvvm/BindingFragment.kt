package com.anymore.andkit.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope
import com.anymore.andkit.lifecycle.fragment.AndkitFragment
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by liuyuanmao on 2019/2/23.
 */
abstract class BindingFragment<BD : ViewDataBinding> : AndkitFragment() {

    protected lateinit var mBinding: BD
    override val mContext by lazy { requireContext() }
    override val mLifecycleOwner by lazy { this }
    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }
    override val hasDestroyed get() = isDetached
    private val TAG = "${this::class.simpleName}#${hashCode()}"
    //用于存储当前Fragment的前一个可见状态
    private var mPreviousVisibleState = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        mBinding.lifecycleOwner = this
        initView(savedInstanceState)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun initView(savedInstanceState: Bundle?) {}

    open fun initData(savedInstanceState: Bundle?) {}

    override fun onResume() {
        Timber.tag(TAG).w("==>onResume()")
        super.onResume()
        setFragmentVisibleState(true)
    }

    override fun onPause() {
        Timber.tag(TAG).w("==>onPause()")
        super.onPause()
        setFragmentVisibleState(false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Timber.tag(TAG).w("==>setUserVisibleHint($isVisibleToUser)")
        super.setUserVisibleHint(isVisibleToUser)
        setFragmentVisibleState(userVisibleHint)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        Timber.tag(TAG).w("==>onHiddenChanged($hidden)")
        super.onHiddenChanged(hidden)
        setFragmentVisibleState(!hidden)
    }

    private fun setFragmentVisibleState(newState: Boolean) {
        if (mPreviousVisibleState.compareAndSet(!newState, newState)) {
            onVisibleStateChanged(newState)
        }
    }

    protected open fun onVisibleStateChanged(visible: Boolean) {
        Timber.tag(TAG).w("==>可见性：$visible")
    }

}