package com.anymore.wanandroid.mine.mvp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.route.MINE_MINE_FRAGMENT

/**
 * Created by anymore on 2020/2/5.
 */
class MineHostActivity : AppCompatActivity(), IActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mine = ARouter.getInstance().build(MINE_MINE_FRAGMENT).navigation() as Fragment
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, mine)
            .commit()
    }

    override fun useFragment() = true
}