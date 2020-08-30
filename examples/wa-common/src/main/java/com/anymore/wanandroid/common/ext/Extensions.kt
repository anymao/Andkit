package com.anymore.wanandroid.common.ext

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.anymore.wanandroid.common.executors.AppExecutors
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by anymore on 2019/4/20.
 */

inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)


inline fun <reified T> Gson.toList(json: String): List<T> {
    return fromJson<List<T>>(json, object : TypeToken<List<T>>() {
    }.type)
}

inline fun CharSequence?.ifNotEmpty(block: () -> Unit) {
    if (!isNullOrBlank()) {
        block()
    }
}

fun Activity.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun Activity.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun Fragment.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun Fragment.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun runOnUiThread(block: () -> Unit) {
    AppExecutors.mainExecutor.execute(block)
}

@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected(): Boolean {
    val networkService: ConnectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info: NetworkInfo? = networkService.activeNetworkInfo
    return info?.isAvailable ?: false
}


fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Context.px2dp(px: Float): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun Context.getDimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}

fun Context.getDimensionPixelSize(@DimenRes id: Int): Int {
    return resources.getDimensionPixelSize(id)
}

/**
 * 兼容获取颜色值资源
 */
fun Context.getColorCompatibly(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

/**
 * 兼容获取Drawable资源
 */
fun Context.getDrawableCompatibly(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)