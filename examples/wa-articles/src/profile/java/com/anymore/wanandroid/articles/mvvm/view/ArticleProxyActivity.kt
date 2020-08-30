package com.anymore.wanandroid.articles.mvvm.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.route.ARTICLES_DISCOVERY_FRAGMENT

/**
 * Created by anymore on 2020/8/29.
 */
class ArticleProxyActivity : AppCompatActivity(), IActivity {
    private lateinit var mContainer: FrameLayout
    private val mFragment by lazy {
        ARouter.getInstance().build(ARTICLES_DISCOVERY_FRAGMENT).navigation() as Fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContainer = FrameLayout(this).apply {
            id = View.generateViewId()
        }
        setContentView(
            mContainer,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        supportFragmentManager.beginTransaction().add(mContainer.id, mFragment).commit()
    }
}