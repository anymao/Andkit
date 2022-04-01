package com.anymore.wanandroid.repository.rpc.response

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class HomeBannerVo(
    val desc: String? = null,
    val id: Int? = null,
    val imagePath: String? = null,
    val isVisible: Int? = null,
    val order: Int? = null,
    val title: String? = null,
    val type: Int? = null,
    val url: String? = null
) : Serializable