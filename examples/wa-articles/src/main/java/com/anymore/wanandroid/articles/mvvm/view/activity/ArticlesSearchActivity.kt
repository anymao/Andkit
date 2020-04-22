package com.anymore.wanandroid.articles.mvvm.view.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.mvvm.BaseActivity
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.WaActivityArticlesSearchBinding
import com.anymore.wanandroid.articles.mvvm.viewmodel.ArticlesSearchViewModel
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.route.ARTICLES_SEARCH
import kotlinx.android.synthetic.main.wa_activity_articles_search.*

/**
 * Created by anymore on 2020/4/17.
 */
@Route(path = ARTICLES_SEARCH)
class ArticlesSearchActivity :
    BaseActivity<WaActivityArticlesSearchBinding, ArticlesSearchViewModel>() {
    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.wa_activity_articles_search
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }
}