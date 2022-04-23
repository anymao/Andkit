package com.anymore.andkit.core.loading

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieAnimationView
import com.anymore.andkit.common.ktx.goneIfEmpty
import com.anymore.andkit.core.R

/**
 * Created by anymore on 2022/4/2.
 */
class LoadingDialog(context: Context) : AppCompatDialog(context, R.style.CoreLoadingDialog1) {

    private var lav: LottieAnimationView? = null
    private var tvMessage: TextView? = null

    var message: CharSequence? = null
        set(value) {
            field = value
            tvMessage?.goneIfEmpty(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.core_dialog_loading_1)
        lav = findViewById(R.id.lav)
        tvMessage = findViewById(R.id.tv_message)
        tvMessage?.text = message
    }

}