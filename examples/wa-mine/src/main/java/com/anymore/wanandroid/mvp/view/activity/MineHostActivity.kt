package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.view.fragment.MineFragment

/**
 * Created by anymore on 2020/2/5.
 */
class MineHostActivity : AppCompatActivity(),IActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wm_activity_mine_host)
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, MineFragment())
            .commit()
    }

    override fun useFragment()=true
}