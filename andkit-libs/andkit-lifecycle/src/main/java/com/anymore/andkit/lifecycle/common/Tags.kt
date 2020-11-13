package com.anymore.andkit.lifecycle.common

/**
 * Created by lym on 2020/11/13.
 */
val Any?.classTag: String get() = this?.javaClass?.name ?: "null"
val Any?.uniqueTag: String
    get() = if (this != null) {
        "${javaClass.name}@${hashCode()}"
    } else {
        "null"
    }