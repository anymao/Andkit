package com.anymore.andkit.rpc

import androidx.collection.LruCache
import retrofit2.Retrofit

/**
 * Created by anymore on 2022/3/29.
 */
@Suppress("UNCHECKED_CAST")
object RpcProvider {
    private val cache = LruCache<Class<*>, Any>(128)

    fun <T> create(retrofit: Retrofit, clazz: Class<T>): T {
        val result = cache[clazz] as? T
        if (result != null) return result
        return retrofit.create(clazz).also {
            cache.put(clazz, it as Any)
        }
    }
}