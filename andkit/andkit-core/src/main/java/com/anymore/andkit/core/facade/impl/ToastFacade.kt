package com.anymore.andkit.core.facade.impl

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.anymore.andkit.common.ktx.applicationContext
import com.anymore.andkit.common.ktx.dp2px
import com.anymore.andkit.core.R
import com.anymore.andkit.core.facade.IToast

internal object ToastFacade : IToast {

    private val handler = Handler(Looper.getMainLooper())
    private var toast: Toast? = null

    private fun makeToast(): Toast {
        val toastView =
            LayoutInflater.from(applicationContext).inflate(R.layout.core_toast_default_1, null)
        return Toast(applicationContext).apply {
            view = toastView
            setGravity(
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                applicationContext.dp2px(63)
            )
            toast = this
        }
    }

    @AnyThread
    override fun toast(message: CharSequence?, duration: Int) {
        toastWithIcon(message, null, duration)
    }

    @AnyThread
    override fun toastWithIcon(message: CharSequence?, icon: Drawable?, duration: Int) {
        if (message.isNullOrEmpty()) return
        runOnUi {
            toast?.cancel()
            makeToast().apply {
                view?.let {
                    it.findViewById<TextView>(R.id.tv_toast)?.text = message
                    val ivToast = it.findViewById<ImageView>(R.id.iv_toast_start)
                    if (ivToast != null) {
                        ivToast.isVisible = icon != null
                        ivToast.setImageDrawable(icon)
                    }
                }
                setDuration(duration)
                show()
            }
        }
    }

    @AnyThread
    override fun toastSuccess(message: CharSequence?, duration: Int) {
        toastWithIcon(message, null, duration)
    }

    @AnyThread
    override fun toastFailed(message: CharSequence?, duration: Int) {
        toastWithIcon(message, null, duration)
    }

    @AnyThread
    override fun cancel() {
        runOnUi {
            toast?.cancel()
        }
    }

    private fun getDrawable(@DrawableRes id: Int): Drawable? =
        ContextCompat.getDrawable(applicationContext, id)

    private fun runOnUi(runnable: Runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }
}