package com.anymore.andkit.mvvm.ext

import androidx.lifecycle.MutableLiveData

/**
 * Created by anymore on 2020/11/15.
 */
fun <T> MutableLiveData<T>.setDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        setValue(value)
    }
}

fun <T> MutableLiveData<T>.postDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        postValue(value)
    }
}