package com.anymore.andkit.common.ktx

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotEmpty != null)
    }
    return !isNullOrEmpty()
}

@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotBlank != null)
    }
    return !isNullOrBlank()
}

/**
 * 如果当前字符串为空或者null，将会被builder所构建的字符串替换
 */
fun CharSequence?.replaceWhenEmpty(builder: () -> CharSequence): CharSequence {
    return if (isNotEmpty()) {
        this
    } else {
        builder()
    }
}