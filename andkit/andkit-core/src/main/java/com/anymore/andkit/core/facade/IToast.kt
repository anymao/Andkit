package com.anymore.andkit.core.facade

import android.graphics.drawable.Drawable

interface IToast {
    fun toast(message: CharSequence?, duration: Int)
    fun toastWithIcon(message: CharSequence?, icon: Drawable?, duration: Int)
    fun toastSuccess(message: CharSequence?, duration: Int)
    fun toastFailed(message: CharSequence?, duration: Int)
    fun cancel()
}