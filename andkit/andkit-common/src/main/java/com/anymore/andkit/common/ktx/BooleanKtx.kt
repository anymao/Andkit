package com.anymore.andkit.common.ktx

/**
 * Created by anymore on 2022/4/11.
 */
fun Boolean?.orTrue() = this?:true

fun Boolean?.orFalse() = this?:false
