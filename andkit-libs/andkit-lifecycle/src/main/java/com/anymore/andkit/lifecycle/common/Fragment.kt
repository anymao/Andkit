package com.anymore.andkit.lifecycle.common

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

/**
 * Created by lym on 2020/11/13.
 */

fun Fragment.putBundle(extra: Parcelable?, extraName: String = EXTRA): Fragment {
    val bundle = Bundle().apply {
        putParcelable(extraName, extra)
    }
    arguments = bundle
    return this
}