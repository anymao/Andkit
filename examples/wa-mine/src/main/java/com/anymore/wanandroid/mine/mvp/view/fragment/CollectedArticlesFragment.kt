package com.anymore.wanandroid.mine.mvp.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpFragment
import com.anymore.wanandroid.mine.entry.Article
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mine.mvp.contract.CollectedArticlesContract
import com.anymore.wanandroid.mine.mvp.view.adapter.ArticlesListAdapter
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.MINE_COLLECTED_ARTICLES_FRAGMENT
import com.anymore.wanandroid.route.URL_VALUE
import kotlinx.android.synthetic.main.wm_fragment_todo_list.*

/**
 * Created by anymore on 2020/2/4.
 */
@Route(path = MINE_COLLECTED_ARTICLES_FRAGMENT)
class CollectedArticlesFragment :
    BaseMvpFragment<CollectedArticlesContract.ICollectedArticlesPresenter>(),
    CollectedArticlesContract.ICollectedArticlesView {


    private val adapter by lazy {
        ArticlesListAdapter(requireContext())
            .also {
                it.setOnClickListener { i, article1 ->
                    ARouter.getInstance()
                        .build(BROWSE_URL)
                        .withString(URL_VALUE, article1.link)
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
        rvList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        srl.setOnRefreshListener {
            refreshList()
        }
        srl.setOnLoadMoreListener {
            mPresenter.loadCollectedArticlesByPage(mCurrentPage)
        }
        refreshList()
    }

    override fun showCollectedArticles(data: List<Article>, pageNum: Int, hasMore: Boolean) {
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