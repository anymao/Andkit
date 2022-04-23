package com.anymore.wanandroid.repository.rpc.interceptor

import com.anymore.andkit.common.ktx.isNotEmpty
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by anymore on 2022/4/7.
 */
class WanAndroidInterceptor @Inject constructor(private val onLogout: () -> Unit) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val builder = response.newBuilder()
        val content = response.body?.string()
        if (response.isSuccessful && content.isNotEmpty()) {
            val json = JSONObject(content)
            val code = json.optLong("errorCode")
            if (code == -1001L) {
                onLogout()
            }
        }
        builder.body(content.orEmpty().toResponseBody(response.body?.contentType()))
        return builder.build()
    }
}