package com.anymore.wanandroid.view

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.R
import com.anymore.wanandroid.common.adapter.FragmentsAdapter
import com.anymore.wanandroid.common.entry.FragmentItem
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.route.ARTICLES_DISCOVERY_FRAGMENT
import com.anymore.wanandroid.route.ARTICLES_HOMEPAGE_FRAGMENT
import com.anymore.wanandroid.route.MAIN_PAGE
import com.anymore.wanandroid.route.MINE_MINE_FRAGMENT
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

@Route(path = MAIN_PAGE)
class MainActivity : AppCompatActivity(), IActivity {

    //利用kotlin属性委托->标准委托中的Delegates.observable实现点击两次返回桌面
    private var lastPressedTime: Long by Delegates.observable(0L) { _, oldValue, newValue ->
        if (newValue - oldValue < 2000) {
            super.onBackPressed()
            //finish() //二选一
        } else {
            toast("再按一次退出应用")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
    }

    private fun setupViewPager() {
        val homepage =
            ARouter.getInstance().build(ARTICLES_HOMEPAGE_FRAGMENT).navigation() as Fragment
        val discovery =
            ARouter.getInstance().build(ARTICLES_DISCOVERY_FRAGMENT).navigation() as Fragment
        val mine = ARouter.getInstance().build(MINE_MINE_FRAGMENT).navigation() as Fragment
        val fragments = listOf(
            FragmentItem(homepage, getString(R.string.home)),
            FragmentItem(discovery, getString(R.string.discovery)),
            FragmentItem(mine, getString(R.string.my))
        )
        val adapter = FragmentsAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bnv.selectedItemId = R.id.action_home
                    }
                    1 -> {
                        bnv.selectedItemId = R.id.action_discovery
                    }
                    2 -> {
                        bnv.selectedItemId = R.id.action_my
                    }
                }
            }

        })

        bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> viewPager.currentItem = 0
                R.id.action_discovery -> viewPager.currentItem = 1
                R.id.action_my -> viewPager.currentItem = 2
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        lastPressedTime = SystemClock.uptimeMillis()
    }

    override fun useFragment() = true
}
