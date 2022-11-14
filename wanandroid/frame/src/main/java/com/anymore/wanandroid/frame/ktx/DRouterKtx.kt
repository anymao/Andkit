package com.anymore.wanandroid.frame.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.CheckResult
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.anymore.andkit.common.ktx.applicationContext
import com.didi.drouter.api.Extend
import com.didi.drouter.router.Request
import com.didi.drouter.router.RouterCallback

/**
 * Created by anymore on 2022/4/7.
 */

/**
 * 标识本次路由需要检查登陆状态，检查登陆状态逻辑处于[GlobalLoginInterceptor]中
 * 适用于那些无法直接将[LoginInterceptor]注解到目标上面的场景
 */
fun Request.checkLogin(check: Boolean = true) = apply {
    putExtra("checkLogin", check)
}


///**
// * DRouter中：native://loan/main?disableRedirect=true&checkLogin=true
// * checkLogin和disableRedirect是以String形式存储在Extra中的，这个时候getBoolean是无法取到的，
// */
//fun Request.getBooleanCompatible(key: String, defaultValue: Boolean = false) =
//    extra.getBooleanCompatible(key, defaultValue)

/**
 * 复制[request]的路由中所携带的参数信息
 */
fun Request.copyArguments(request: Request) = apply {
    putExtras(request.extra)
    addition.putAll(request.addition)
}

/**
 * 根据现有路由参数，构造新的请求
 */
@CheckResult
fun Request.newRequest() = Request.build(uri.toString()).copyArguments(this)

fun Request.start(context: Context, onActivityResult: (Int, Intent?) -> Unit) {
    start(
        context,
        object : RouterCallback.ActivityCallback() {

            override fun onActivityResult(resultCode: Int, data: Intent?) {
                onActivityResult(resultCode, data)
            }
        }
    )
}

fun Request.start(context: Context, onActivityResultOk: (Intent?) -> Unit) {
    start(context) { resultCode, data ->
        if (resultCode == Activity.RESULT_OK) {
            onActivityResultOk(data)
        }
    }
}

fun Request.navigateFragmentOrNull(): Fragment? {
    var fragment: Fragment? = null
    putExtra(Extend.START_FRAGMENT_NEW_INSTANCE, true)
    start(
        applicationContext
    ) { result -> fragment = result.fragment }
    return fragment
}

fun Request.navigateFragment(): Fragment = requireNotNull(navigateFragmentOrNull()) {
    "导航到Fragment的路由${uri}不存在"
}

fun Request.navigateDialogFragmentOrNull(): DialogFragment? {
    val fragment: Fragment? = navigateFragmentOrNull()
    return if (fragment is DialogFragment) {
        fragment
    } else {
        null
    }
}

fun Request.navigateDialogFragment(): DialogFragment =
    requireNotNull(navigateDialogFragmentOrNull()) {
        "导航到DialogFragment的路由${uri}不存在"
    }

/**
 * 必须传递[context],而且不能传application
 */
fun Request.navigateViewOrNull(context: Context): View? {
    var view: View? = null
    putExtra(Extend.START_VIEW_NEW_INSTANCE, true)
    start(
        context
    ) { result -> view = result.view }
    return view
}

fun Request.navigateView(context: Context): View = requireNotNull(navigateViewOrNull(context)) {
    "导航到View的路由${uri}不存在"
}