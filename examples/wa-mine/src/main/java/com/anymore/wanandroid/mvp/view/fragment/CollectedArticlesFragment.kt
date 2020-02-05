package com.anymore.wanandroid.mvp.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpFragment
import com.anymore.wanandroid.entry.Article1
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.contract.CollectedArticlesContract
import com.anymore.wanandroid.mvp.view.adapter.ArticlesListAdapter
import com.anymore.wanandroid.route.BROWSE_URL
import kotlinx.android.synthetic.main.wm_fragment_todo_list.*

/**
 * Created by anymore on 2020/2/4.
 */
class CollectedArticlesFragment :
    BaseMvpFragment<CollectedArticlesContract.ICollectedArticlesPresenter>(),
    CollectedArticlesContract.ICollectedArticlesView {


    private val adapter by lazy {
        ArticlesListAdapter(requireContext())
            .also {
                it.setOnClickListener { i, article1 ->
                    ARouter.getInstance()
                        .build(BROWSE_URL)
                        .withString("url", article1.link)
                        .navigation(requireContext())
                }
                it.setOnUncollectedListener { i, article1 ->
                    mPresenter.cancelCollectArticle(i, article1.id)
                }
            }
    }

    private var mCurrentPage = firstPage

    companion object {
        const val firstPage = 0
    }

    override fun getLayoutRes() = R.layout.wm_fragment_collected_articles

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        rvList.adapter = adapter
        srl.setOnRefreshListener {
            refreshList()
        }
        srl.setOnLoadMoreListener {
            mPresenter.loadCollectedArticlesByPage(mCurrentPage)
        }
        refreshList()
    }

    override fun showCollectedArticles(data: List<Article1>, pageNum: Int, hasMore: Boolean) {
        if (pageNum == firstPage + 1) {
            adapter.setData(data)
            srl.finishRefresh()
//            if (todos.isNullOrEmpty()){
//                toast("这里☞空空的~")
//            }
        } else {
            adapter.addData(data)
            srl.finishLoadMore()
        }
        mCurrentPage = pageNum
        srl.setNoMoreData(!hasMore)
    }

    override fun refreshOrLoadFailed(refresh: Boolean) {
        if (refresh) {
            srl.finishRefresh(false)
        } else {
            srl.finishLoadMore(false)
        }
        showError("收藏列表加载失败!")
    }

    override fun remove(index: Int) {
        adapter.remove(index)
    }

    private fun refreshList() {
        mPresenter.refreshCollectedArticles()
    }
}