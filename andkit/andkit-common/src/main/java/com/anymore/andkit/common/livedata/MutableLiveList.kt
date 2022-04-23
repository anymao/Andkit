package com.anymore.andkit.common.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MutableLiveList<T>(value: Collection<T> = emptyList()) :
    MutableLiveData<MutableList<T>>(value.toMutableList()) {

    /**
     * 当[observe]或者[observeForever]的时候会触发
     */
    var onObserved: (() -> Unit)? = null

    val size: Int get() = value.size

    val isEmpty get() = value.isEmpty()

    fun contains(item: T) = value.contains(item)

    fun update(newValues: Collection<T>) {
        with(value) {
            clear()
            addAll(newValues)
        }
        update()
    }

    fun addAll(index: Int, newValues: List<T>) {
        if (index in value.indices) {
            value.addAll(index, newValues)
            update()
        }
    }

    fun addAll(newValues: List<T>) {
        value.addAll(newValues)
        update()
    }

//    /**
//     * 当不存在的时候才添加
//     */
//    fun addIfAbsent(item: T) {
//        if (value.addIfAbsent(item)) {
//            update()
//        }
//    }

    fun add(item: T) {
        value.add(item)
        update()
    }

    fun add(index: Int, item: T) {
        if (index in value.indices) {
            value.add(index, item)
            update()
        }
    }

    fun removeAt(index: Int): Boolean {
        if (index in value.indices) {
            value.removeAt(index)
            update()
            return true
        }
        return false
    }

    fun remove(item: T): Boolean {
        if (item in value) {
            value.remove(item)
            update()
            return true
        }
        return false
    }

    fun clear() {
        value.clear()
        update()
    }

    fun update() {
        postValue(value)
    }

    override fun getValue(): MutableList<T> {
        return super.getValue() ?: mutableListOf()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in MutableList<T>>) {
        super.observe(owner, observer)
        onObserved?.invoke()
    }

    override fun observeForever(observer: Observer<in MutableList<T>>) {
        super.observeForever(observer)
        onObserved?.invoke()
    }


}