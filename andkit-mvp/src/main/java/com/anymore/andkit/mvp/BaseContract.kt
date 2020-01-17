package com.anymore.andkit.mvp

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * MVP架构基础契约类
 * Created by liuyuanmao on 2019/7/15.
 */
interface BaseContract {
    /**
     * View层基础契约接口
     */
    interface IBaseView {

        /**
         * 向Presenter层提供当前View的[LifecycleOwner]对象，这对于在Presenter层
         * 使用像Uber的AutoDispose这种库时候十分有用
         */
        fun provideLifecycleOwner(): LifecycleOwner
        /**
         * 通知UI目前处于loading状态，在[BaseActivity]和[BaseFragment]中有默认实现的弹窗形式LoadingDialog
         * 如果界面需要使用嵌入式的loading控件，请重写
         */
        fun showProgressBar(message:String,cancelable: Boolean = true)

        /**
         * 通知UI loading结束
         */
        fun hideProgressBar()

        /**
         * UI界面给出一条积极的提示
         */
        fun showSuccess(@StringRes stringId: Int)
        fun showSuccess(message: String)
        /**
         * UI界面给出一条消极的提示
         */
        fun showError(@StringRes stringId: Int)
        fun showError(message: String)
    }

    /**
     *Presenter层基础接口
     */
    interface IBasePresenter: LifecycleObserver {
        /**
         * Presenter层的初始化操作，绑定在View层的OnCreate时机
         */
        fun onCreate(){
            Timber.d("==>onCreate()")
        }

        /**
         * Presenter层的清除操作，绑定在View层的OnDestroy时机
         */
        fun onDestroy(){
            Timber.d("==>onDestroy()")
        }
    }

    /**
     * Model层基础接口
     */
    interface IBaseModel{
        /**
         * Model层的清理操作，由调用方执行
         */
        fun clear(){}
    }
}