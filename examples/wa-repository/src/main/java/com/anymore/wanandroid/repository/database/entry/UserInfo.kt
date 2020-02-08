package com.anymore.wanandroid.repository.database.entry


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
    val admin: Boolean,
    val chapterTops: List<String>,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String,
    var online:Boolean = false
)
