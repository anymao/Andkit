package com.anymore.wanandroid.mvvm.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseFragment
import com.anymore.wanandroid.adapter.KnowledgesAdapter
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaFragmentDiscoveryBinding
import com.anymore.wanandroid.common.ext.toast
import com.anymore.wanandroid.entry.Knowledge
import com.anymore.wanandroid.mvvm.viewmodel.DiscoveryViewModel
import com.anymore.wanandroid.route.*

/**
 * Created by anymore on 2020/1/27.
 */
@Route(path = ARTICLES_DISCOVERY_FRAGMENT)
class DiscoveryFragment : BaseFragment<WaFragmentDiscoveryBinding, DiscoveryViewModel>() {

    private val mAdapter by lazy {
        KnowledgesAdapter(context!!).apply {
            mItemEventHandler = object : KnowledgesAdapter.OnItemEventHandler {
                override fun onClick(item: Knowledge) {
                    if (item.children.size == 1) {
                        val onlyChild = item.children[0]
                        val bundle = Bundle()
                        bundle.putInt(CID, onlyChild.id)
                        ARouter.getInstance()
                            .build(BROWSE_FRAGMENT)
                            .withString(TITLE, onlyChild.name)
                            .withString(FRAGMENT_ROUTE, ARTICLES_KNOWLEDGES_ARTICLES_FRAGMENT)
                            .withBundle(PARAMS, bundle)
                            .navigation(requireActivity())

                    } else {
                        ARouter.getInstance()
                            .build(ARTICLES_KNOWLEDGE)
                            .withSerializable(KONWLEDGES, item)
                            .navigation(requireActivity())
                    }
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        ARouter.getInstance().inject(this)
        return R.layout.wa_fragment_discovery
    }

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