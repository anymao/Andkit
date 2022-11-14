package com.anymore.andkit.core.ktx

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.anymore.andkit.common.ktx.applicationContext
import com.anymore.andkit.core.facade.IToast
import com.anymore.andkit.core.facade.impl.ToastFacade

/**
 * Created by anymore on 2022/4/9.
 */

@Volatile
private var impl: IToast = ToastFacade

/**
 * 全局统一风格的toast，如果后续上层要替换，调用此方法
 */
fun setToastFacadeImpl(facade: IToast) {
    impl = facade
}

fun toast(message: CharSequence?, toastLong: Boolean = false) {
    impl.toast(
        message, if (toastLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    )
}

fun toastSuccess(message: CharSequence?, toastLong: Boolean = false) {
    impl.toastSuccess(
        message, if (toastLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    )
}

fun toastFailed(message: CharSequence?, toastLong: Boolean = false) {
    impl.toastFailed(
        message, if (toastLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    )
}

fun toastWithIcon(message: CharSequence?, @DrawableRes id: Int, toastLong: Boolean = false) {
    impl.toastWithIcon(
        message,
        ContextCompat.getDrawable(applicationContext, id),
        if (toastLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    )
}

fun cancelToast() {
    impl.cancel()
}