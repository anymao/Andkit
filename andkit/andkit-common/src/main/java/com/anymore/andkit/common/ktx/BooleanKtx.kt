package com.anymore.andkit.common.ktx

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false