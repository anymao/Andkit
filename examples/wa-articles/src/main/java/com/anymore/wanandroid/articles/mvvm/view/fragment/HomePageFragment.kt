package com.anymore.wanandroid.articles.mvvm.view.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseFragment
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.adapter.ArticlesPagingAdapter
import com.anymore.wanandroid.articles.databinding.WaFragmentHomepageBinding
import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.articles.entry.Banner
import com.anymore.wanandroid.articles.loader.BannerLoader
import com.anymore.wanandroid.articles.mvvm.viewmodel.HomePageViewModel
import com.anymore.wanandroid.route.ARTICLES_HOMEPAGE_FRAGMENT
import com.anymore.wanandroid.route.ARTICLES_SEARCH
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.URL_VALUE
import kotlinx.android.synthetic.main.wa_fragment_homepage.*

@Route(path = ARTICLES_HOMEPAGE_FRAGMENT)
class HomePageFragment : BaseFragment<WaFragmentHomepageBinding, HomePageViewModel>() {
    private lateinit var mBannerLoader: BannerLoader
    private val mAdapter by lazy {
        ArticlesPagingAdapter(context!!).apply {
            mItemEventHandler = object : ArticlesPagingAdapter.OnItemEventHandler {
                override fun onClick(item: Article) {
                    ARouter.getInstance()
                        .build(BROWSE_URL)
                        .withString(URL_VALUE, item.link)
                        .navigation(requireContext())
                }

                override fun onCollectClick(item: Article) {
                    with(item) {
                        collect = !collect
                    }
                }
            }
        }
    }

    override fun getLayoutRes() = R.layout.wa_fragment_homepage

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        initBanner()
        initRecyclerView()
        mViewModel.mBanners.observe(this, Observer { it?.run { setBanners(this) } })
        mViewModel.mArticleListing.pagedList.observe(this, Observer { mAdapter.submitList(it) })
        mViewModel.mArticleListing.status.observe(this, Observer { mAdapter.netStatus = it })
        mViewModel.mArticleListing.retry.observe(this, Observer { mAdapter.retry = it })
    }

    override fun onResume() {
        super.onResume()
        mBannerLoader.startAutoPlay()
    }

    override fun onPause() {
        mBannerLoader.stopAutoPlay()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.wa_search_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                ARouter.getInstance().build(ARTICLES_SEARCH).navigation(requireContext())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initBanner() {
        mBannerLoader = BannerLoader(mBinding.banner)
        mBannerLoader.initBanner {
            ARouter.getInstance()
                .build(BROWSE_URL)
                .withString(URL_VALUE, it.url)
                .navigation(requireContext())
        }
    }

    private fun setBanners(banners: List<Banner>) {
        mBannerLoader.loadData(banners)
    }


    private fun initRecyclerView() {
        mBinding.rvArticles.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mBinding.rvArticles.adapter = mAdapter
    }
}