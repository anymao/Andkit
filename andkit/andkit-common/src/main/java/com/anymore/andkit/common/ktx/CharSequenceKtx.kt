package com.anymore.andkit.common.ktx

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by anymore on 2022/4/6.
 */
@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotEmpty != null)
    }
    return !isNullOrEmpty()
}