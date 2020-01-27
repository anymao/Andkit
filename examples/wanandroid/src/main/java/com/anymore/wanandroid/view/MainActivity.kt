package com.anymore.wanandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.R
import com.anymore.wanandroid.adapter.FragmentsAdapter
import com.anymore.wanandroid.entry.FragmentItem
import com.anymore.wanandroid.mvvm.view.HomePageFragment
import com.anymore.wanandroid.route.MAIN_PAGE
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = MAIN_PAGE)
class MainActivity : AppCompatActivity(), IActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            FragmentItem(HomePageFragment(), getString(R.string.home)),
            FragmentItem(Fragment(), getString(R.string.discovery)),
            FragmentItem(Fragment(), getString(R.string.my))
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

    override fun useFragment() = true
}
