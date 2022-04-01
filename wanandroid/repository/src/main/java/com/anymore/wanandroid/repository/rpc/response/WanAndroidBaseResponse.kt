package com.anymore.wanandroid.repository.rpc.response

import androidx.annotation.Keep
import com.anymore.andkit.rpc.ErrorResponseException
import com.anymore.andkit.rpc.ResponseWrapper
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class WanAndroidBaseResponse<T>(
    @SerializedName("data")
    override val `data`: T? = null,
    @SerializedName("errorCode")
    override val code: Long? = null,
    @SerializedName("errorMsg")
    override val message: String? = null
) : Serializable, ResponseWrapper<T> {
    companion object {
        const val CODE_SUCCESS = 0
    }

    override fun check(vararg codes: Long) {
        codes.forEach {
            if (it == code) return
        }
        throw ErrorResponseException(code = code, message = message)
    }
}