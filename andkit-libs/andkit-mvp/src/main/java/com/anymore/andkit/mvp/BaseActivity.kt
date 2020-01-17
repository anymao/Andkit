package com.anymore.andkit.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.andkit.mvp.widget.LoadingDialog

/**
 * BaseActivity 实现[BaseContract.IBaseView]接口，后续MVP在Activity作为View层时候
 * 如无必需，无需重复实现其接口
 * Created by liuyuanmao on 2019/7/16.
 */
abstract class BaseActivity : AppCompatActivity(), IActivity, BaseContract.IBaseView {

    protected lateinit var mLoadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initView(savedInstanceState))
        initData(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?): Int

    open fun initData(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        hideProgressBar()
        super.onDestroy()
    }

    override fun provideLifecycleOwner()=this

    override fun showProgressBar(message: String, cancelable: Boolean) {
        showDefaultLoadingDialog(message,cancelable)
    }

    override fun hideProgressBar() {
        if (this::mLoadingDialog.isInitialized && mLoadingDialog.isShowing){
            mLoadingDialog.dismiss()
        }
    }

    override fun showSuccess(stringId: Int) {
        showSuccess(getString(stringId))
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun showError(stringId: Int) {
        showError(getString(stringId))
    }

    override fun showError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun showDefaultLoadingDialog(message: String, cancelable: Boolean){
        if (!this::mLoadingDialog.isInitialized){
            mLoadingDialog = LoadingDialog(this,message,cancelable)
        }else{
            mLoadingDialog.setTitle(message)
            mLoadingDialog.setCancelable(cancelable)
        }
        mLoadingDialog.show()
    }

}
