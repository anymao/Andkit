package com.anymore.wanandroid.repository.rpc.request

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by anymore on 2022/4/2.
 */
@Keep
data class RegisterBody(
    var username: String? = null,
    var password: String? = null,
    var repassword: String? = null
) : Serializable
