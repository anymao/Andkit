package com.anymore.baike.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Created by lym on 2020/11/13.
 */
@Parcelize
data class BaikeResult(
    val resultCode: Int,
    val keyWords: String,
    val baikeContent: String?,
    val baikeContent2: String?,
    val baikeUrl: String?
) :Serializable, Parcelable {
    companion object {
        const val SUCCESS = 0
        const val NETWORK_FAILED = 1
        const val NOT_FOUND = 2
    }
}