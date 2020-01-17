package com.anymore.wanandroid.common.delegates

import android.content.Context
import com.anymore.wanandroid.common.ContextProvider


/**
 * Created by liuyuanmao on 2019/6/18.
 */
class SharedPreferenceHelper(private val spName: String = "default") {
    private val application by lazy { ContextProvider.getApplicationContext() }
    private val sharedPreferences by lazy { application.getSharedPreferences(spName, Context.MODE_PRIVATE) }


    fun put(name: String, value: Any) = with(sharedPreferences.edit()) {
        when (value) {
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            is Long -> putLong(name, value)
            else -> throw IllegalArgumentException("type ${value.javaClass} is not support save in SharedPreferences")
        }
        apply()
    }

    fun putStrings(name: String, stringSet:Set<String>)= with(sharedPreferences.edit()){
        putStringSet(name,stringSet)
        apply()
    }

    fun <T : Any> get(name: String, default: T): T = with(sharedPreferences) {
        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        when (default) {
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            is Long -> getLong(name, default)
            else -> throw IllegalArgumentException("type ${default.javaClass} is not support")
        } as T
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getStings(name: String, default: MutableSet<String> = mutableSetOf()):MutableSet<String> = sharedPreferences.getStringSet(name,default)?:default
}