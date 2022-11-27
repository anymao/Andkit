package com.anymore.andkit.common.ktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.SystemClock
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

fun View.string(@StringRes id: Int, vararg formatArgs: Any): String = context.string(id, formatArgs)
fun View.dimenF(@DimenRes id: Int) = context.dimenF(id)
fun View.dimen(@DimenRes id: Int) = context.dimen(id)

fun View.dp2pxF(dp: Float): Float = context.dp2pxF(dp)
fun View.dp2pxF(dp: Int): Float = context.dp2pxF(dp)
fun View.dp2px(dp: Float) = context.dp2px(dp)
fun View.dp2px(dp: Int) = context.dp2px(dp)
fun View.sp2px(sp: Int) = context.sp2px(sp)
fun View.sp2pxF(sp: Int) = context.sp2pxF(sp)


val View.centerX
    get() = left + width / 2

val View.centerY
    get() = top + height / 2

val View.isRTL: Boolean
    get() = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

/**
 * 递归设置view enable or disable
 */
fun View.setEnableRecursively(enable: Boolean) {
    isEnabled = enable
    if (this is ViewGroup) {
        children.forEach {
            it.setEnableRecursively(enable)
        }
    }
}

/**
 * 将view保存为bitmap，注意bitmap需要被inflate
 */
fun View?.asBitmap(): Bitmap? {
    if (this == null) return null
    if (this.width <= 0 || this.height <= 0) return null
    return tryOrNull {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        draw(canvas)
        bitmap
    }
}

//键盘相关操作扩展
fun View.hideKeyboard() {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    if (imm.isActive) {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.showKeyboard() {
    post {
        requestFocus()
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return@post
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

//TextView相关扩展

/**
 * 利用Flow处理当前TextView(其实真实场景是EditText)，在textChanged之后会触发Flow中的数据流
 * [buffer]同时对数据流默认背压策略是CONFLATED：表示保留最新，send调用后就存放在channel里直接返回，但是channel里只能存放最近一次send的值。
 * [debounce]限流
 */
@OptIn(FlowPreview::class)
fun TextView.afterTextChangedFlow(
    buffer: Int = Channel.CONFLATED,
    debounce: Long = 300L
): Flow<Editable?> {
    return callbackFlow {
        val watcher = doAfterTextChanged {
            trySend(it)
        }
        awaitClose { removeTextChangedListener(watcher) }
    }.buffer(buffer).debounce(debounce)
}

/**
 * 设置文本给textView并且当text为空时候，textView隐藏
 */
fun TextView.goneIfEmpty(text: CharSequence?) {
    this.text = text
    this.isGone = text.isNullOrEmpty()
}

/**
 * 设置文本给textView并且当text为空白时候，textView隐藏
 */
fun TextView.goneIfBlank(text: CharSequence?) {
    this.text = text
    this.isGone = text.isNullOrBlank()
}

//RecyclerView相关扩展
/**
 * RecyclerView关闭Item动画，用于解决刷新item的时候，闪烁问题
 */
fun RecyclerView.disableItemAnimator() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}

//CompoundButton

fun CompoundButton.onCheckChanged(block: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked ->
        block(isChecked)
    }
}


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

fun View.getString(@StringRes idRes: Int) = context.getString(idRes)
fun View.getString(@StringRes idRes: Int, vararg args: Any) = context.getString(idRes, args)