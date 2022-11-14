package com.anymore.andkit.common.ktx

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Activity.viewBinding(inflater: (LayoutInflater) -> T) = lazy {
    inflater(layoutInflater)
}