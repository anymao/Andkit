package com.anymore.wanandroid.route.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.wanandroid.common.delegates.SharedPreferenceDelegate
import com.anymore.wanandroid.route.EXTRA_NEED_LOGIN
import com.anymore.wanandroid.route.INTERCEPTOR_LOGIN
import com.anymore.wanandroid.route.NEED_LOGIN_FLAG
import com.anymore.wanandroid.route.USER_LOGIN

/**
 * Created by anymore on 2020/2/7.
 */
@Interceptor(name = INTERCEPTOR_LOGIN, priority = 9)
class LoginInterceptor : IInterceptor {

    private var mContext: Context? = null
    private val isLogin: Boolean by SharedPreferenceDelegate(name = "is_login", default = false)

    override fun init(context: Context?) {
        mContext = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard?.extras?.getInt(EXTRA_NEED_LOGIN)?: 0
        val needLogin = (postcard?.extra ?: 0) and NEED_LOGIN_FLAG
        if (needLogin > 0 || flag > 0) {
            if (isLogin) {
                callback?.onContinue(postcard)
            } else {
                callback?.onInterrupt(Throwable("用户未登录"))
                ARouter.getInstance()
                    .build(USER_LOGIN)
                    .navigation(mContext)
            }
        } else {
            callback?.onContinue(postcard)
        }
    }

}