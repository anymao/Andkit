package com.anymore.andkit.common.ktx


//临时方案防止除0崩溃
fun Number.zeroOne() = if (this == 0) 1 else this

fun Int?.orZero(): Int = this ?: 0
fun Float?.orZero(): Float = this ?: 0F
fun Long?.orZero(): Long = this ?: 0L
fun Double?.orZero(): Double = this ?: 0.0