package com.anymore.wanandroid.widget.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by anymore on 2022/4/6.
 */
open class FragmentAdapter(
    private val fragmentCreators: List<() -> Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    final override fun getItemCount() = fragmentCreators.size

    final override fun createFragment(position: Int) = fragmentCreators[position]()

}