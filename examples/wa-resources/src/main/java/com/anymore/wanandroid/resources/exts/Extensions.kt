package com.anymore.wanandroid.resources.exts

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * 扩展方法
 * Created by anymore on 2020/3/28.
 */
internal fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

internal fun Context.px2dp(px: Float): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

internal fun Context.getDimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}

internal fun Context.getDimensionPixelSize(@DimenRes id: Int): Int {
    return resources.getDimensionPixelSize(id)
}

/**
 * 兼容获取颜色值资源
 */
internal fun Context.getColorCompatibly(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

/**
 * 兼容获取Drawable资源
 */
internal fun Context.getDrawableCompatibly(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)