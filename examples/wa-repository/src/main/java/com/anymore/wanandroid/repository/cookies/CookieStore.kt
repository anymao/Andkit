package com.anymore.wanandroid.repository.cookies

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import okhttp3.Cookie
import okhttp3.HttpUrl
import timber.log.Timber
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by liuyuanmao on 2019/5/5.
 */
interface CookieStore {

    /**  添加cookie  */
    fun add(httpUrl: HttpUrl, cookie: Cookie)

    /** 添加指定httpurl cookie集合  */
    fun add(httpUrl: HttpUrl, cookies: List<Cookie>)

    /** 根据HttpUrl从缓存中读取cookie集合  */
    fun get(httpUrl: HttpUrl): MutableList<Cookie>

    /** 获取全部缓存cookie  */
    fun getCookies(): MutableList<Cookie>

    /**  移除指定httpurl cookie集合  */
    fun remove(httpUrl: HttpUrl, cookie: Cookie): Boolean

    /** 移除所有cookie  */
    fun removeAll(): Boolean
}

class SharedPreferencesCookieStore : CookieStore {

    companion object {
        private const val DEFAULT_SP_NAME = "Ok_Cookies.sp"//默认sp文件
        private const val HOST_NAME_PREFIX = "host_"//host key前缀
        private const val COOKIE_NAME_PREFIX = "cookie_"//cookie key前缀
    }

    private val mCookies: ConcurrentHashMap<String, ConcurrentHashMap<String, Cookie>>
    private val mSharedPreferences: SharedPreferences
    var omitNonPersistentCookies = false

    constructor(context: Context) : this(context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE))

    constructor(sharedPreferences: SharedPreferences) {
        this.mSharedPreferences = sharedPreferences
        this.mCookies = ConcurrentHashMap()
        val cookiesMap = HashMap<String, Any>(mSharedPreferences.all)
        for (key in cookiesMap.keys) {
            if (!key.startsWith(HOST_NAME_PREFIX)) {
                continue
            }

            val cookieNames = cookiesMap[key] as String
            if (TextUtils.isEmpty(cookieNames)) {
                continue
            }

            if (!mCookies.containsKey(key)) {
                mCookies[key] = ConcurrentHashMap()
            }

            val cookieNameArr = cookieNames.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (name in cookieNameArr) {
                val encodedCookie = mSharedPreferences.getString(COOKIE_NAME_PREFIX + name, null) ?: continue
                val decodedCookie = SerializableCookie().decode(encodedCookie)
                decodedCookie?.let {
                    Timber.d("decodedCookie is :$it")
                    mCookies[key]!![name] = it
                }
            }
        }
        cookiesMap.clear()
        clearExpired()
    }

    /** 移除失效cookie  */
    private fun clearExpired() {
        val editor = mSharedPreferences.edit()

        for (key in this.mCookies.keys) {
            var changeFlag = false
            for ((name, cookie) in mCookies[key]!!) {
                if (isCookieExpired(cookie)) {
                    // Clear mCookies from local store
                    mCookies[key]!!.remove(name)
                    // Clear mCookies from persistent store
                    editor.remove(COOKIE_NAME_PREFIX + name)
                    // We've cleared at least one
                    changeFlag = true
                }
            }

            // Update names in persistent store
            if (changeFlag) {
                editor.putString(key, TextUtils.join(",", mCookies.keys))
            }
        }

        editor.apply()
    }

    override fun add(httpUrl: HttpUrl, cookie: Cookie) {
        if (omitNonPersistentCookies && !cookie.persistent) {
            return
        }

        val name = cookieName(cookie)
        val hostKey = hostName(httpUrl)

        // Save cookie into local store, or remove if expired
        if (!mCookies.containsKey(hostKey)) {
            mCookies[hostKey] = ConcurrentHashMap()
        }
        mCookies[hostKey]!![name] = cookie

        // Save cookie into persistent store
        val editor = mSharedPreferences.edit()
        // 保存httpUrl对应的所有cookie的name
        editor.putString(hostKey, TextUtils.join(",", mCookies[hostKey]!!.keys))
        // 保存cookie
        editor.putString(COOKIE_NAME_PREFIX + name, SerializableCookie().encode(cookie))
        editor.apply()
    }

    override fun add(httpUrl: HttpUrl, cookies: List<Cookie>) {
        for (cookie in cookies) {
            if (isCookieExpired(cookie)) {
                continue
            }
            add(httpUrl, cookie)
        }
    }

    override fun get(httpUrl: HttpUrl): MutableList<Cookie> {
        return get(hostName(httpUrl))
    }

    override fun getCookies(): MutableList<Cookie> {
        val result = ArrayList<Cookie>()
        for (hostKey in mCookies.keys) {
            result.addAll(get(hostKey))
        }
        return result
    }

    /** 获取cookie集合  */
    private fun get(hostKey: String): MutableList<Cookie> {
        val result = ArrayList<Cookie>()
        if (mCookies.containsKey(hostKey)) {
            val cookies = mCookies[hostKey]!!.values
            for (cookie in cookies) {
                if (isCookieExpired(cookie)) {
                    remove(hostKey, cookie)
                } else {
                    result.add(cookie)
                }
            }
        }
        return result
    }

    override fun remove(httpUrl: HttpUrl, cookie: Cookie): Boolean {
        return remove(hostName(httpUrl), cookie)
    }

    /** 从缓存中移除cookie  */
    private fun remove(hostKey: String, cookie: Cookie): Boolean {
        val name = cookieName(cookie)
        if (mCookies.containsKey(hostKey) && mCookies[hostKey]!!.containsKey(name)) {
            // 从内存中移除httpUrl对应的cookie
            mCookies[hostKey]?.remove(name)

            val editor = mSharedPreferences.edit()

            // 从本地缓存中移出对应cookie
            editor.remove(COOKIE_NAME_PREFIX + name)

            // 保存httpUrl对应的所有cookie的name
            editor.putString(hostKey, TextUtils.join(",", mCookies[hostKey]!!.keys))

            editor.apply()
            return true
        }
        return false
    }

    override fun removeAll(): Boolean {
        val editor = mSharedPreferences.edit()
        editor.clear()
        editor.apply()
        mCookies.clear()
        return true
    }


    /** 判断cookie是否失效   */
    private fun isCookieExpired(cookie: Cookie): Boolean {
        return cookie.expiresAt < System.currentTimeMillis()
    }

    private fun hostName(httpUrl: HttpUrl): String {
        return if (httpUrl.host.startsWith(HOST_NAME_PREFIX)) httpUrl.host else HOST_NAME_PREFIX + httpUrl.host
    }

    private fun cookieName(cookie: Cookie): String {
        return cookie.name + "@" + cookie.domain
    }

}