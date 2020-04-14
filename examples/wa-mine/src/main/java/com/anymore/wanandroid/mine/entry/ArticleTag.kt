package com.anymore.wanandroid.mine.entry

import java.io.Serializable

/**
 * 文章tag
 */
data class ArticleTag(
    val name: String,
    val url: String
) : Serializable