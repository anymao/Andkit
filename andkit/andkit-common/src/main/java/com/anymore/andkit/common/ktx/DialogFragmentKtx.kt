package com.anymore.andkit.common.ktx

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.anymore.andkit.common.ktx.className

fun DialogFragment.show(manager: FragmentManager) {
    show(manager, className)
}