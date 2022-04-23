package com.anymore.andkit.core.base

/**
 * Created by anymore on 2022/3/29.
 */
interface Loadable {

    val delegate: LoadingDelegate

    fun showLoading(text: String?="正在加载...") {
        delegate.showLoading(text)
    }

    fun hideLoading() {
        delegate.hideLoading()
    }
}