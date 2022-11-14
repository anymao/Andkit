package com.anymore.andkit.common.ktx

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.anymore.andkit.common.ApplicationContextProvider
import java.util.*
import kotlin.math.roundToInt


val applicationContext: Context by lazy { ApplicationContextProvider.application }

fun Context.findActivity(): Activity? {
    if (this is Application) return null
    if (this is Activity) return this
    if (this is ContextWrapper) return this.baseContext.findActivity()
    return null
}

fun Context.themeColor(
    @AttrRes id: Int,
    @ColorInt default: Int = Color.WHITE
): Int = themeValue(theme, id)?.let {
    if (it.resourceId == 0) {
        it.data
    } else {
        tryOr(default) {
            color(it.resourceId)
        }
    }
} ?: default

fun Context.themeValue(
    theme: Resources.Theme,
    @AttrRes id: Int
): TypedValue? {
    val value = TypedValue()
    val resolved = theme.resolveAttribute(id, value, true)
    return if (resolved) {
        value
    } else {
        null
    }
}

fun Context.themeDrawable(
    @AttrRes id: Int
): Drawable? = themeValue(theme, id)?.let {
    try {
        drawableOrNull(it.resourceId)
    } catch (e: Exception) {
        null
    }
}

fun Context.drawableOrNull(
    @DrawableRes id: Int,
    bound: Boolean = false,
    @ColorRes filterColor: Int? = null
): Drawable? =
    ContextCompat.getDrawable(this, id)?.let { drawable ->
        if (bound) {
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        }
        filterColor?.let {
            drawable.mutate().apply {
                colorFilter = PorterDuffColorFilter(color(it), PorterDuff.Mode.SRC_IN)
            }
        } ?: drawable
    }

fun Context.drawable(
    @DrawableRes id: Int,
    bound: Boolean = false,
    @ColorRes filterColor: Int? = null
) =
    drawableOrNull(id, bound, filterColor) ?: Color.TRANSPARENT

fun Context.color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.colorDrawable(@ColorRes id: Int) = ColorDrawable(color(id))

fun Context.string(@StringRes id: Int, vararg formatArgs: Any): String =
    resources.getString(id, formatArgs)

fun Context.dimenF(@DimenRes id: Int) = resources.getDimension(id)
fun Context.dimen(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun Context.dp2pxF(dp: Float): Float = dp * resources.displayMetrics.density
fun Context.dp2pxF(dp: Int): Float = dp2pxF(dp.toFloat())
fun Context.dp2px(dp: Float) = dp2pxF(dp).roundToInt()
fun Context.dp2px(dp: Int) = dp2pxF(dp).roundToInt()
fun Context.sp2pxF(sp: Int): Float = sp * resources.displayMetrics.scaledDensity
fun Context.sp2px(sp: Int): Int = sp2pxF(sp).roundToInt()

@Suppress("DEPRECATION")
val Context.locale: Locale
    get() = ConfigurationCompat.getLocales(applicationContext.resources.configuration).get(0)
        ?: applicationContext.resources.configuration.locale