package com.anymore.wanandroid.entry

import java.io.Serializable

/**
 * 文章tag
 */
data class ArticleTag(
    val name: String,
    val url: String
) : Serializable