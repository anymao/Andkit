package com.anymore.wanandroid.repository.database.entry


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import com.anymore.wanandroid.repository.database.converters.UserInfoConverter
import kotlinx.android.parcel.Parcelize

/**
 * Created by anymore on 2019/4/20.
 */
@Entity
@TypeConverters(UserInfoConverter::class)
@Parcelize
data class UserInfo(
    var admin: Boolean,
    var chapterTops: List<String>,
    var collectIds: List<Int>,
    var email: String,
    var icon: String,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String,
    var online: Boolean = false
) : BaseModel(), Parcelable
