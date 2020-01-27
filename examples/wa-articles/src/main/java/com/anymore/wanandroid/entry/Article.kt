package com.anymore.wanandroid.entry

import androidx.databinding.BaseObservable
import java.io.Serializable

/**
 * 每一条文章的信息
 */
data class Article(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<ArticleTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) : BaseObservable(), Serializable {
    var collect: Boolean = false
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.collect)
//        }
//        @Bindable get
}

