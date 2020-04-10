package com.anymore.wanandroid.mvvm.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseFragment
import com.anymore.wanandroid.adapter.ArticlesPagingAdapter
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaFragmentKnowledgesArticlesBinding
import com.anymore.wanandroid.entry.Article
import com.anymore.wanandroid.mvvm.viewmodel.KnowledgesArticlesViewModel
import com.anymore.wanandroid.route.ARTICLES_KNOWLEDGES_ARTICLES_FRAGMENT
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.CID
import com.anymore.wanandroid.route.URL_VALUE

/**
 * Created by liuyuanmao on 2019/4/30.
 */
@Route(path = ARTICLES_KNOWLEDGES_ARTICLES_FRAGMENT)
class KnowledgesArticlesFragment :
    BaseFragment<WaFragmentKnowledgesArticlesBinding, KnowledgesArticlesViewModel>() {


    @Autowired(name = CID, required = true, desc = "分类id")
    @JvmField
    var cid: Int = 0

    private val mAdapter by lazy { ArticlesPagingAdapter(context!!) }

    override fun getLayoutRes(): Int {
        ARouter.getInstance().inject(this)
        return R.layout.wa_fragment_knowledges_articles
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initRecycleView()
        mViewModel.getKnowledgesArticlesListing(cid).pagedList.observe(
            this,
            Observer { mAdapter.submitList(it) })
    }


    private fun initRecycleView() {
        mBinding.rvArticles.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mAdapter.mItemEventHandler = object : ArticlesPagingAdapter.OnItemEventHandler {
            override fun onClick(item: Article) {
                ARouter.getInstance()
                    .build(BROWSE_URL)
                    .withString(URL_VALUE, item.link)
                    .navigation(requireActivity())
            }

            override fun onCollectClick(item: Article) {

            }
        }
        mBinding.rvArticles.adapter = mAdapter
    }

}