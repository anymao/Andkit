package com.anymore.wanandroid.web.view

import com.anymore.andkit.common.ktx.applicationContext
import com.anymore.wanandroid.frame.router.WanAndroidRouter.web
import com.didi.drouter.annotation.Router
import com.didi.drouter.api.DRouter
import com.didi.drouter.router.IRouterHandler
import com.didi.drouter.router.Request
import com.didi.drouter.router.Result

/**
 * Created by anymore on 2022/4/23.
 */
@Router(scheme = "http|https", host = ".*", path = ".*")
class HttpSchemeHandler : IRouterHandler {
    override fun handle(request: Request, result: Result) {
        val context = request.context ?: applicationContext
        val uri = request.uri.toString()
        DRouter.build(web).putExtra("url", uri).start(context)
    }
}