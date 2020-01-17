package com.anymore.wanandroid.repository.database.entry


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.annotation.NonNull
import com.anymore.wanandroid.repository.database.converters.UserInfoConverter

/**
 * Created by anymore on 2019/4/20.
 */
@Entity
@TypeConverters(UserInfoConverter::class)
data class UserInfo(

    @NonNull
    @PrimaryKey
    val id: Int,
    val username: String,
    val password: String,
    val token: String,
    val type: Int,
    val email: String,
    val icon: String,
    val chapterTops: List<String>,
    val collectIds: List<String>,
    var online:Boolean = false
)