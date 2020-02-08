package com.anymore.andkit.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.anymore.andkit.lifecycle.fragment.IFragment
import com.anymore.andkit.mvp.widget.LoadingDialog
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * BaseFragment 实现[BaseContract.IBaseView]接口，后续MVP在[Fragment]作为View层时候
 * 如无必需，无需重复实现其接口
 * Created by liuyuanmao on 2019/7/16.
 */
abstract class BaseFragment : Fragment(), IFragment, BaseContract.IBaseView {

    private val TAG = "BaseFragment@${hashCode()}"
    //用于存储当前Fragment的前一个可见状态
    private var mPreviousVisibleState = AtomicBoolean(false)
    protected lateinit var mLoadingDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
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
        Timber.tag(TAG).w("${hashCode()}:onResume()")
        super.onResume()
        setFragmentVisibleState(true)
    }

    override fun onPause() {
        Timber.tag(TAG).w("${hashCode()}:onPause()")
        super.onPause()
        setFragmentVisibleState(false)
    }

    override fun onDestroyView() {
        hideProgressBar()
        super.onDestroyView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Timber.tag(TAG).w("${hashCode()}:setUserVisibleHint($isVisibleToUser)")
        super.setUserVisibleHint(isVisibleToUser)
        setFragmentVisibleState(userVisibleHint)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        Timber.tag(TAG).w("${hashCode()}:onHiddenChanged($hidden)")
        super.onHiddenChanged(hidden)
        setFragmentVisibleState(!hidden)
    }

    private fun setFragmentVisibleState(newState: Boolean) {
        if (mPreviousVisibleState.compareAndSet(!newState, newState)) {
            onVisibleStateChanged(newState)
        }
    }

    protected open fun onVisibleStateChanged(visible: Boolean) {
        Timber.tag(TAG).w("Fragment(${this.hashCode()})可见性：$visible")
    }

    override fun provideLifecycleOwner() = this

    override fun showProgressBar(message: String, cancelable: Boolean) {
        showDefaultLoadingDialog(message, cancelable)
    }

    override fun hideProgressBar() {
        if (this::mLoadingDialog.isInitialized && mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    override fun showSuccess(stringId: Int) {
        showSuccess(getString(stringId))
    }

    override fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(stringId: Int) {
        showError(getString(stringId))
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showDefaultLoadingDialog(message: String, cancelable: Boolean) {
        hideProgressBar()
        mLoadingDialog = LoadingDialog(requireContext(), message, cancelable).also {
            it.title = message
        }
        mLoadingDialog.setCancelable(cancelable)
        mLoadingDialog.show()
    }
}