package com.anymore.wanandroid.articles.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anymore.wanandroid.articles.entry.Article


/**
 * Created by liuyuanmao on 2019/4/19.
 */
class ArticlesSourceFactory(private val provider: ArticlesProvider) :
    DataSource.Factory<Int, Article>() {
    val source = MutableLiveData<ArticlesSource>()
    override fun create(): DataSource<Int, Article> {
        val s = ArticlesSource(provider)
        source.postValue(s)
        return s
    }
}