package com.anymore.wanandroid.articles.paging

import com.anymore.wanandroid.articles.entry.Article
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.paging.PagingProvider

/**
 * 使用Provider将真实的数据请求细节屏蔽，统一那些仅仅是获取方式不同，而输出结果格式一致的api接口
 * 抽象玩Android文章列表获取接口
 * Created by liuyuanmao on 2019/4/22.
 */
interface ArticlesProvider : PagingProvider<PagedData<Article>>