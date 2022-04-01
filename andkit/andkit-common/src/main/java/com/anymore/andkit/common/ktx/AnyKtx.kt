package com.anymore.andkit.common.ktx

/**
 * Created by anymore on 2022/3/30.
 */
val Any.className get() = this::class.qualifiedName

val Any.uniqueTag get() = "$className@${hashCode()}"