package com.anymore.wanandroid.repository.rpc.response


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Keep
@Parcelize
data class ArticleTagVo(
    var name: String? = null,
    var url: String? = null
) : Serializable, Parcelable