package com.anymore.andkit.core.base

/**
 * Created by anymore on 2022/3/29.
 */
interface Loadable {
    fun showLoading(text: String?)
    fun hideLoading()
}