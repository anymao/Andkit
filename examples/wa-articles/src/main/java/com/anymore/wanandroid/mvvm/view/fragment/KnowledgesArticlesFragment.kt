package com.anymore.wanandroid.mvvm.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseFragment
import com.anymore.wanandroid.adapter.ArticlesPagingAdapter
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaFragmentKnowledgesArticlesBinding
import com.anymore.wanandroid.entry.Article
import com.anymore.wanandroid.mvvm.viewmodel.KnowledgesArticlesViewModel
import com.anymore.wanandroid.route.BROWSE_URL

/**
 * Created by liuyuanmao on 2019/4/30.
 */
class KnowledgesArticlesFragment :
    BaseFragment<WaFragmentKnowledgesArticlesBinding, KnowledgesArticlesViewModel>() {

    companion object {
        const val EXTRA_CID = "EXTRA_CID"

        fun newInstance(cid: Int): KnowledgesArticlesFragment {
            val bundle = Bundle()
            bundle.putInt(EXTRA_CID, cid)
            return newInstance(bundle)
        }

        fun newInstance(bundle: Bundle): KnowledgesArticlesFragment {
            val fragment = KnowledgesArticlesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mAdapter by lazy { ArticlesPagingAdapter(context!!) }

    override fun getLayoutRes() = R.layout.wa_fragment_knowledges_articles

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initRecycleView()
        val cid = getCid()
        mViewModel.getKnowledgesArticlesListing(cid).pagedList.observe(
            this,
            Observer { mAdapter.submitList(it) })
    }

    private fun getCid(): Int {
        val bundle = arguments
        return bundle?.getInt(EXTRA_CID) ?: 0
    }

    private fun initRecycleView() {
        mBinding.rvArticles.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mBinding.rvArticles.layoutManager =
            LinearLayoutManager(context)
        mAdapter.mItemEventHandler = object : ArticlesPagingAdapter.OnItemEventHandler {
            override fun onClick(item: Article) {
                ARouter.getInstance()
                    .build(BROWSE_URL)
                    .withString("url", item.link)
                    .navigation(requireActivity())
            }

            override fun onCollectClick(item: Article) {

            }
        }
        mBinding.rvArticles.adapter = mAdapter
    }

}