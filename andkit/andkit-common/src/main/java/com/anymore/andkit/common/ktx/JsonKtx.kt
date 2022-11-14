package com.anymore.andkit.common.ktx

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 默认的gson配置，如果不满足序列化配置，修改这个gson即可
 */
@Volatile
private var gson: Gson = Gson()

fun setGsonImpl(impl: Gson) {
    gson = impl
}

/**
 * 将实体类转为json串
 */
fun Any.toJson(type: Type = this.javaClass): String = gson.toJson(this, type)

fun <T : Any> String?.toEntry(clazz: Class<T>): T? = this?.run { gson.fromJson(this, clazz) }

inline fun <reified T : Any> String?.toEntry(): T? = toEntry(T::class.java)

fun <T : Any> String?.toEntryListOrNull(clazz: Class<T>): List<T>? = this?.run {
    tryOrNull {
        gson.fromJson(this, object : ParameterizedType {
            override fun getActualTypeArguments() = arrayOf(clazz)

            override fun getRawType() = List::class.java

            override fun getOwnerType(): Type? = null
        })
    }
}

inline fun <reified T : Any> String?.toEntryListOrNull(): List<T>? =
    toEntryListOrNull(T::class.java)

fun <T : Any> String?.toEntryList(clazz: Class<T>): List<T> = toEntryListOrNull(clazz).orEmpty()

inline fun <reified T : Any> String?.toEntryList(): List<T> = toEntryList(T::class.java)

