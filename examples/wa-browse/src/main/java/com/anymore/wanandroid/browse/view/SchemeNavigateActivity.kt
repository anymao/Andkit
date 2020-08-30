package com.anymore.wanandroid.browse.view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.browse.R
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.route.*

/**
 * Created by anymore on 2020/4/17.
 */
class SchemeNavigateActivity : AppCompatActivity(), IActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wb_activity_scheme_navigate)
        val uri: Uri? = intent.data
        if (uri == null) {
            finish()
            return
        }
        if (uri.scheme == SCHEME_WANANDROID && uri.host == "browse"){
            val browseUrl = uri.getQueryParameter("url")
            if (!browseUrl.isNullOrEmpty()){
                ARouter.getInstance()
                    .build(BROWSE_URL)
                    .withString(URL_VALUE, browseUrl)
                    .navigation(this, object : NavCallback() {
                        override fun onArrival(postcard: Postcard?) {
                            finish()
                        }
                    })
            }else{
                toast("请求参数不合法")
            }
            return
        }
        when (uri.scheme) {
            SCHEME_HTTP, SCHEME_HTTPS -> ARouter.getInstance()
                .build(BROWSE_URL)
                .withString(URL_VALUE, uri.toString())
                .navigation(this, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        finish()
                    }
                })
        }
    }
}