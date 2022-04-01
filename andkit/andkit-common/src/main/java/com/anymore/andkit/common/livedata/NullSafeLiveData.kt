package com.anymore.andkit.common.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Created by anymore on 2022/3/29.
 */
@Suppress("UNCHECKED_CAST")
class NullSafeLiveData<T>(value: T) : MutableLiveData<T>(value) {

    companion object {
        fun <T> empty() = NullSafeLiveData<T?>(null)
    }

    override fun getValue(): T = super.getValue() as T
}