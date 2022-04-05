package com.anymore.andkit.core.base

import com.anymore.andkit.common.loader.LoadCallback
import timber.log.Timber

/**
 * Created by anymore on 2022/4/2.
 */
interface LoadingDelegate :
    LoadCallback {

    fun showLoading(text: String? = "正在加载...")

    fun hideLoading()

    override fun onPrepare() {
        showLoading()
    }

    override fun onSuccess() {

    }

    override fun onFailed(throwable: Throwable?): Boolean {
        Timber.e(throwable)
        return true
    }

    override fun onFinal() {
        hideLoading()
    }
}