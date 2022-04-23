package com.anymore.wanandroid.frame.ktx

import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import com.anymore.andkit.common.ktx.applicationContext
import com.anymore.andkit.core.base.ComponentContext
import com.anymore.wanandroid.frame.router.WanAndroidRouter
import com.didi.drouter.api.DRouter
import com.didi.drouter.api.Extend

/**
 * Created by anymore on 2022/4/7.
 */

fun route(path: String) = DRouter.build(path)


fun routeFragmentOrNull(path: String): Fragment? {
    var fragment: Fragment? = null
    DRouter.build(path).putExtra(Extend.START_FRAGMENT_NEW_INSTANCE, true)
        .start(
            applicationContext
        ) { result -> fragment = result.fragment }
    return fragment
}

fun routeFragment(path: String) = requireNotNull(routeFragmentOrNull(path))

fun routeViewOrNull(path: String): View? {
    var view: View? = null
    DRouter.build(path).putExtra(Extend.START_VIEW_NEW_INSTANCE, true)
        .start(
            applicationContext
        ) { result -> view = result.view }
    return view
}

fun routeView(path: String) = requireNotNull(routeViewOrNull(path))

fun ComponentContext.go(path: String) {
    route(path).start(ccContext)
}

fun ComponentContext.go(uri: Uri) {
    go(uri.toString())
}

fun ComponentContext.startFlutter(
    schema: String = WanAndroidRouter.flutterScheme,
    host: String = WanAndroidRouter.flutterHost,
    path: String
) {
    go(Uri.parse("$schema://$host$path"))
}