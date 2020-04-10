package com.anymore.wanandroid.mvp.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.anymore.wanandroid.mine.R
import kotlinx.android.synthetic.main.wm_view_flutter_loading.view.*

/**
 * Created by anymore on 2020/4/9.
 */
class FlutterLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.wm_view_flutter_loading, this)
    }

    fun removeSelf() {
        val animation = provideAnim()
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val parent = parent as ViewGroup
                parent.removeView(this@FlutterLoadingView)
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        llLoading.startAnimation(animation)
    }

    private fun provideAnim(): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.wm_loading_exit_anim)
    }

}