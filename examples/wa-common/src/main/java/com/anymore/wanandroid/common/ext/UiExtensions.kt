package com.anymore.wanandroid.common.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * Created by liuyuanmao on 2019/11/4.
 */

/**
 * 修改状态栏为全透明
 */
@SuppressLint("NewApi")
fun Activity.setTransparencyBar() {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        return
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        return
    }
}

/**
 * 状态栏亮色模式，设置状态栏黑色文字、图标，
 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
 *
 * @return 1:MIUUI 2:Flyme 3:android6.0
 */
fun Activity.setStatusBarLightMode(): Int {
    var result = 0
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        when {
            setMIUIStatusBarLightMode(true) -> //小米
                result = 1
            setFlymeStatusBarLightMode(true) -> //魅族
                result = 2
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                //6.0以上
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
            else -> {
                //其他的都设置状态栏成半透明的,以下设置半透明是调用第三方的，根据个人需求更改
                //    ImmersionBar.with(activity).statusBarDarkFont(true, 0.5f).init();
            }
        }
    }
    return result
}

/**
 * 设置状态栏图标为深色和魅族特定的文字风格
 * 可以用来判断是否为Flyme用户
 *
 * @param dark 是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
fun Activity.setFlymeStatusBarLightMode(dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}

/**
 * 需要MIUIV6以上
 *
 * @param dark  是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
@SuppressLint("PrivateApi")
fun Activity.setMIUIStatusBarLightMode(dark: Boolean): Boolean {
    var result = false
    val window = window
    if (window != null) {
        val clazz = window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField =
                clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (dark) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        } catch (e: Exception) {

        }

    }
    return result
}

/**
 * 仅app module可见
 * 设置toolbar状态
 */
fun AppCompatActivity.setupToolbar(toolbar: Toolbar, click: ((view: View) -> Unit)? = null) {
    title = ""
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    toolbar.setNavigationOnClickListener {
        if (click != null) {
            click.invoke(it)
        } else {
            finish()
        }
    }
}