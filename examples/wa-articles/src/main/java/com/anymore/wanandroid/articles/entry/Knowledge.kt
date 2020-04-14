package com.anymore.wanandroid.articles.entry

import java.io.Serializable

/**
 * 请求到的所有知识体系的tag封装
 */
data class Knowledge(
    val children: List<Knowledge>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable