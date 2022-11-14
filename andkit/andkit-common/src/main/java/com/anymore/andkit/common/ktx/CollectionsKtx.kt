package com.anymore.andkit.common.ktx

fun Collection<*>?.isEmpty() = this?.isEmpty() ?: true

fun Collection<*>?.isNotEmpty() = !isEmpty()

//基于当前集合，返回新的ArrayList
fun <T> Collection<T>?.newArrayList(): ArrayList<T> = ArrayList(this.orEmpty())

fun Map<*, *>?.isEmpty() = isNullOrEmpty()

fun Map<*, *>?.isNotEmpty() = !isNullOrEmpty()

/**
 * 当集合中不存在目标值才添加进来
 */
fun <T> MutableCollection<T>.addIfAbsent(item: T): Boolean {
    if (!contains(item)) {
        add(item)
        return true
    }
    return false
}

