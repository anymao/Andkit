package com.anymore.wanandroid.browse

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.SCHEME_HTTP
import com.anymore.wanandroid.route.SCHEME_HTTPS
import com.anymore.wanandroid.route.URL_VALUE

/**
 * Created by anymore on 2020/4/17.
 */
class SchemeNavigateActivity : AppCompatActivity(), IActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: Uri? = intent.data
        if (uri == null) {
            finish()
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