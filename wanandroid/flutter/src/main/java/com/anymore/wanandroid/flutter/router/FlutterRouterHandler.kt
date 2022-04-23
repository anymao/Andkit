package com.anymore.wanandroid.flutter.router

import com.anymore.andkit.common.ktx.applicationContext
import com.anymore.wanandroid.frame.router.WanAndroidRouter.flutterHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.flutterScheme
import com.didi.drouter.annotation.Router
import com.didi.drouter.router.IRouterHandler
import com.didi.drouter.router.Request
import com.didi.drouter.router.Result
import io.flutter.embedding.android.FlutterActivity

/**
 * Created by anymore on 2022/4/23.
 */
@Router(scheme = flutterScheme, host = flutterHost, path = ".*")
class FlutterRouterHandler : IRouterHandler {
    override fun handle(request: Request, result: Result) {
        val context = request.context ?: applicationContext
        val uri = request.uri
        val path = uri.path ?: return
        context.startActivity(FlutterActivity.withNewEngine().initialRoute(path).build(context))
    }
}