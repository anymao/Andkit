package com.anymore.wanandroid.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.browse.R
import com.anymore.wanandroid.common.ext.ifNotEmpty
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.route.BROWSE_FRAGMENT
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
    @Autowired
    @JvmField
    var title: String? = ""

    /**
     * 界面中的Fragment的全路径
     */
    @Autowired
    @JvmField
    var fragmentName: String? = ""


    /**
     * 传递给fragment的参数
     */
    @Autowired
    @JvmField
    var params: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.wb_activity_fragment)
        setupToolbar(toolbar)
        toolbar.title = title ?: ""
        injectFragment()
    }

    private fun injectFragment() {
        if (fragmentName.isNullOrEmpty()) {
            Toast.makeText(this, "参数错误，启动界面失败！", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val fragment = instantiateFragment<Fragment>(fragmentName!!, params)
        fragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, this)
                .commit()
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
                fragment = fragmentFactory.instantiate(classLoader, fragmentName!!) as F
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