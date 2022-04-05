package com.anymore.andkit.core.loading

import android.content.Context
import com.anymore.andkit.core.base.LoadingDelegate

/**
 * Created by anymore on 2022/4/2.
 */
class DialogLoadingDelegateImpl(private val context: Context) : LoadingDelegate {
    private var dialog: LoadingDialog? = null
    override fun showLoading(text: String?) {
        if (dialog == null) {
            dialog = LoadingDialog(context)
        }
        dialog?.run {
            message = text
            if (!isShowing) {
                show()
            }
        }

    }

    override fun hideLoading() {
        dialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }

}