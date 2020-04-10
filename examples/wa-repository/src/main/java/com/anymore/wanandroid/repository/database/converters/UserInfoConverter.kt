package com.anymore.wanandroid.repository.database.converters

import androidx.room.TypeConverter
import com.anymore.wanandroid.common.ext.toList
import com.google.gson.Gson

/**
 * Created by anymore on 2019/4/20.
 */
class UserInfoConverter {

    @TypeConverter
    fun stringList2Json(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun json2StringList(json: String) = Gson().toList<String>(json)

    @TypeConverter
    fun intList2Json(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun json2IntList(json: String) = Gson().toList<Int>(json)
}
