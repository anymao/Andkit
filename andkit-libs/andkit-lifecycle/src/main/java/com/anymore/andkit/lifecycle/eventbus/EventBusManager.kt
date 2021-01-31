package com.anymore.andkit.lifecycle.eventbus

import org.greenrobot.eventbus.EventBus

/**
 * Created by anymore on 2021/1/31.
 */
object EventBusManager {
    private val eventBus by lazy { EventBus.getDefault() }

    fun register(subscriber: Any) {
        eventBus.register(subscriber)
    }

    fun unregister(subscriber: Any) {
        eventBus.unregister(subscriber)
    }

    fun post(event: Any) {
        eventBus.post(event)
    }

    fun postSticky(event: Any) {
        eventBus.postSticky(event)
    }
}