package com.anymore.andkit.core.ktx

import android.widget.Toast
import androidx.annotation.StringRes
import com.anymore.andkit.common.ktx.applicationContext

/**
 * Created by anymore on 2022/4/9.
 */

fun toast(@StringRes res: Int) {
    toast(applicationContext.getString(res))
}

fun toast(message: CharSequence?) {
    if (message.isNullOrBlank()) return
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun toastFailed(@StringRes res: Int) {
    toastFailed(applicationContext.getString(res))
}

fun toastFailed(message: CharSequence?) {
    toast(message)
}

fun toastSuccess(@StringRes res: Int) {
    toastSuccess(applicationContext.getString(res))
}

fun toastSuccess(message: CharSequence?) {
    toast(message)
}