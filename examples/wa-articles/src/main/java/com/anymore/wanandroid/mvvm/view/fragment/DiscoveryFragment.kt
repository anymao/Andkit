package com.anymore.wanandroid.mvvm.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseFragment
import com.anymore.wanandroid.adapter.KnowledgesAdapter
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaFragmentDiscoveryBinding
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.entry.Knowledge
import com.anymore.wanandroid.mvvm.viewmodel.DiscoveryViewModel
import com.anymore.wanandroid.route.ARTICLES_KNOWLEDGE
import com.anymore.wanandroid.route.BROWSE_FRAGMENT

/**
 * Created by anymore on 2020/1/27.
 */
class DiscoveryFragment : BaseFragment<WaFragmentDiscoveryBinding, DiscoveryViewModel>() {

    private val mAdapter by lazy {
        KnowledgesAdapter(context!!).apply {
            mItemEventHandler = object : KnowledgesAdapter.OnItemEventHandler {
                override fun onClick(item: Knowledge) {
                    if (item.children.size == 1) {
                        val onlyChild = item.children[0]
                        val bundle = Bundle()
                        bundle.putInt(KnowledgesArticlesFragment.EXTRA_CID, onlyChild.id)
                        ARouter.getInstance()
                            .build(BROWSE_FRAGMENT)
                            .withString("title", onlyChild.name)
                            .withString("fragmentName", KnowledgesArticlesFragment::class.java.name)
                            .withBundle("params", bundle)
                            .navigation(requireActivity())

                    } else {
                        ARouter.getInstance()
                            .build(ARTICLES_KNOWLEDGE)
                            .withSerializable("knowledge", item)
                            .navigation(requireActivity())
                    }
                }
            }
        }
    }

    override fun getLayoutRes() = R.layout.wa_fragment_discovery

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.mAllKnowledges.observe(this, Observer { it?.run { mAdapter.setData(this) } })
        mViewModel.mToast.observe(this, Observer { toast(it) })
        initRecycleView()
        mViewModel.loadAllKnowledges()
    }

    private fun initRecycleView() {
        mBinding.rvKnowledges.layoutManager = GridLayoutManager(context, 3)
        mBinding.rvKnowledges.adapter = mAdapter
    }

}