package com.anymore.wanandroid.mvvm.view.activity

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaActivityKnowledgesTabBinding
import com.anymore.wanandroid.common.adapter.FragmentsAdapter
import com.anymore.wanandroid.common.entry.FragmentItem
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.entry.Knowledge
import com.anymore.wanandroid.mvvm.view.fragment.KnowledgesArticlesFragment
import com.anymore.wanandroid.route.ARTICLES_KNOWLEDGE

/**
 * Created by liuyuanmao on 2019/4/30.
 */
@Route(path = ARTICLES_KNOWLEDGE)
class KnowledgesTabActivity : BindingActivity<WaActivityKnowledgesTabBinding>() {

    @Autowired
    @JvmField
    var knowledge:Knowledge? = null

    override fun initView(savedInstanceState: Bundle?):Int{
        ARouter.getInstance().inject(this)
        return R.layout.wa_activity_knowledges_tab
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupToolbar(mBinding.toolbar)
        if (knowledge != null) {
            setUpViewPagers(knowledge!!)
        } else {
            toast("传递参数时候出错!", Toast.LENGTH_LONG)
            finish()
        }

    }

    private fun setUpViewPagers(knowledge: Knowledge) {
        val fragments = ArrayList<FragmentItem>()
        for (i in knowledge.children) {
            if (i.visible == 1) {
                fragments.add(FragmentItem(KnowledgesArticlesFragment.newInstance(i.id), i.name))
            }
        }
        mBinding.viewPager.adapter = FragmentsAdapter(supportFragmentManager, fragments)
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        title = knowledge.name
    }

    override fun useFragment() = true

}