package com.anymore.wanandroid.view

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.browse.R
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.URL_VALUE
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import kotlinx.android.synthetic.main.wb_activity_web.*

/**
 * 单纯的web页面
 * Created by anymore on 2020/1/25.
 */
@Route(path = BROWSE_URL)
class WebActivity : AppCompatActivity(), IActivity {

    protected lateinit var mAgentWeb: AgentWeb

    @Autowired(name = URL_VALUE, required = true, desc = "访问的web地址")
    @JvmField
    var url: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.wb_activity_web)
        setupToolbar(toolbar)
        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContainer, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(webChromeClient)
            .createAgentWeb()
            .ready()
            .go(url)
        with(mAgentWeb.agentWebSettings.webSettings) {
            useWideViewPort = true
            loadWithOverviewMode = true
        }
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}