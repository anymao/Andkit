package com.anymore.wanandroid.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.anymore.wanandroid.entry.FragmentItem

class FragmentsAdapter(fm: FragmentManager, private val fragments: List<FragmentItem>) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getItem(position: Int) = fragments[position].fragment

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].title
    }

}