package com.anymore.wanandroid.browse.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.browse.R
import com.anymore.wanandroid.common.ext.ifNotEmpty
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.route.*
import kotlinx.android.synthetic.main.wb_activity_fragment.*
import timber.log.Timber

/**
 * Created by anymore on 2020/1/25.
 */
@Route(path = BROWSE_FRAGMENT)
class FragmentActivity : AppCompatActivity(), IActivity {

    /**
     * 界面标题
     */
    @Autowired(name = TITLE, desc = "界面的标题")
    @JvmField
    var title: String? = ""

    /**
     * 界面中的Fragment的全路径
     */
    @Autowired(name = FRAGMENT_NAME, desc = "界面容纳的Fragment的全路径")
    @JvmField
    var fragmentName: String? = ""

    @Autowired(name = FRAGMENT_ROUTE, desc = "界面容纳的Fragment的路由名")
    @JvmField
    var fragmentRoute: String? = ""


    /**
     * 传递给fragment的参数
     */
    @Autowired(name = PARAMS, desc = "传递给Fragment的参数")
    @JvmField
    var params: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.wb_activity_fragment)
        setupToolbar(toolbar)
        setTitle(title ?: "")
        injectFragment()
    }

    private fun injectFragment() {
        val fragment = getFragmentFromArgs()
        if (fragment == null) {
            toast("参数错误，启动界面失败！")
            finish()
            return
        }
        fragment.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, this)
                .commit()
        }
    }

    private fun getFragmentFromArgs(): Fragment? {
        return if (!fragmentRoute.isNullOrEmpty()) {
            ARouter.getInstance().build(fragmentRoute).with(params).navigation() as Fragment
        } else if (!fragmentName.isNullOrEmpty()) {
            instantiateFragment(fragmentName!!, params)
        } else {
            null
        }
    }

    private fun <F : Fragment> instantiateFragment(
        fragmentName: String,
        params: Bundle? = null
    ): F? {
        var fragment: F? = null
        fragmentName.ifNotEmpty {
            try {
                val fragmentFactory = supportFragmentManager.fragmentFactory
                @Suppress("UNCHECKED_CAST")
                fragment = fragmentFactory.instantiate(classLoader, fragmentName) as F
                params?.apply {
                    this.classLoader = this@FragmentActivity.classLoader
                    fragment?.arguments = this
                }
            } catch (e: Exception) {
                Timber.e(e, "Start A FragmentActivity failed!")
            }
        }
        return fragment
    }

    override fun useFragment() = true

}