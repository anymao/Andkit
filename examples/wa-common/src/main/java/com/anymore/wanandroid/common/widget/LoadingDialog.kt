package com.anymore.wanandroid.common.widget

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.anymore.wanandroid.common.R
import kotlinx.android.synthetic.main.wc_dialog_loading.*

/**
 * Created by liuyuanmao on 2020/1/17.
 */
class LoadingDialog(context: Context, title: String? = null, cancelable: Boolean = true) :
    AlertDialog(context, R.style.wc_loading_dialog) {

    var title: String? = ""
        set(value) {
            field = value
            if (field.isNullOrEmpty()) {
                tvTitle?.text = ""
            } else {
                tvTitle?.text = field
            }
        }

    init {
        this.title = title
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wc_dialog_loading)
        title?.let { tvTitle.text = it }
    }


    override fun setTitle(titleId: Int) {
        setTitle(context.getString(titleId))
    }

    override fun setTitle(title: CharSequence?) {
        this.title = title.toString()
    }
}
