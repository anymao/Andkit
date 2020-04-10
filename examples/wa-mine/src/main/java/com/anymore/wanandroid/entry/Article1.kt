package com.anymore.wanandroid.entry

import java.io.Serializable

/**
 * 每一条文章的信息
 */
data class Article1(
    var apkLink: String,
    var author: String,
    var chapterId: Int,
    var chapterName: String,
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: List<ArticleTag1>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int,
    var collect: Boolean = false
) : Serializable
