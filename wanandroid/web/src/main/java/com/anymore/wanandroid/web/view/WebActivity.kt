package com.anymore.wanandroid.web.view

import android.view.KeyEvent
import android.webkit.WebView
import android.widget.LinearLayout
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.frame.router.WanAndroidRouter.web
import com.anymore.wanandroid.web.R
import com.anymore.wanandroid.web.databinding.WebActivityWebBinding
import com.didi.drouter.annotation.Router
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by anymore on 2022/4/8.
 */
@AndroidEntryPoint
@Router(scheme = naScheme, host = naHost, path = web)
open class WebActivity : BaseDataBindingActivity<WebActivityWebBinding>() {

    override fun getLayoutRes() = R.layout.web_activity_web


    protected lateinit var mAgentWeb: AgentWeb

    val url by lazy { intent?.getStringExtra("url") }

    override fun initView() {
        super.initView()
        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                binding.toolbar.title = title
            }
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webContainer, LinearLayout.LayoutParams(-1, -1))
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