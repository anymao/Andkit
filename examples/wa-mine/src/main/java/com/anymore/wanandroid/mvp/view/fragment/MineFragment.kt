package com.anymore.wanandroid.mvp.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseFragment
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.route.MINE_TODO
import kotlinx.android.synthetic.main.wm_fragment_mine.*

/**
 * Created by anymore on 2020/1/29.
 */
class MineFragment:BaseFragment() {

    override fun getLayoutRes()= R.layout.wm_fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        tvTodo.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_TODO)
                .navigation(requireContext())
        }
    }
}