package com.anymore.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.ViewAnimator
import com.airbnb.lottie.LottieAnimationView
import com.anymore.andkit.common.ktx.click
import com.anymore.andkit.common.loader.LoadCallback
import com.anymore.andkit.common.loader.Loader

/**
 * Created by anymore on 2022/4/7.
 */
class LoadingButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewAnimator(context, attrs), LoadCallback, Loader {

    private val lav:LottieAnimationView
    private val textView:TextView
//    private var text:String = ""

    init {
        View.inflate(context,R.layout.widget_loading_button,this)
        lav = findViewById(R.id.lb_lav)
        textView = findViewById(R.id.lb_text)
        textView.click { load() }
    }

    override fun onPrepare() {
        displayedChild = 1
        lav.playAnimation()
    }

    override fun onSuccess() {
        displayedChild = 0
        lav.pauseAnimation()
    }

    override fun onFailed(throwable: Throwable?): Boolean {
        displayedChild = 0
        lav.pauseAnimation()
        return true
    }

    override fun onFinal() {

    }

    override var loader: (() -> Unit)? = null
}