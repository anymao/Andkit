package com.anymore.andkit.mvvm.base

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

/**
 * Created by anymore on 2022/3/29.
 */
interface LayoutProvider {

    @LayoutRes
    fun getLayoutRes(): Int

    @StringRes
    fun getTitleRes(): Int
}