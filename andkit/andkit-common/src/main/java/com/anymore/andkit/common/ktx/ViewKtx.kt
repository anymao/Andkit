package com.anymore.andkit.common.ktx

import android.os.SystemClock
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible

/**
 * Created by anymore on 2022/4/5.
 */
private class DurationOnClickListener(
    private val duration: Long,
    private val block: ((View?) -> Unit)
) :
    View.OnClickListener {
    private var lastTrigger = 0L

    override fun onClick(v: View?) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastTrigger > duration) {
            block(v)
            lastTrigger = now
        }
    }

}

fun View.click(duration: Long = 500L, block: ((View?) -> Unit)? = null) {
    if (block != null) {
        setOnClickListener(DurationOnClickListener(duration, block))
    } else {
        setOnClickListener(null)
    }
}

fun TextView.goneIfEmpty(text: CharSequence?) {
    this.text = text
    this.isVisible = text.isNotEmpty()
}

fun View.getString(@StringRes idRes: Int) = context.getString(idRes)
fun View.getString(@StringRes idRes: Int, vararg args: Any) = context.getString(idRes, args)