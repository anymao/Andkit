package com.anymore.wanandroid.repository.rpc.response

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LoginVo(
    var admin: Boolean? = null,
    var chapterTops: List<Long>? = null,
    var coinCount: Int? = null,
    var collectIds: List<Long>? = null,
    var email: String? = null,
    var icon: String? = null,
    var id: Int? = null,
    var nickname: String? = null,
    var password: String? = null,
    var publicName: String? = null,
    var token: String? = null,
    var type: Int? = null,
    var username: String? = null
) : Serializable