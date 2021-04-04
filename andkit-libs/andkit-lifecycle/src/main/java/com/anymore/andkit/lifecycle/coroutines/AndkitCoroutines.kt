package com.anymore.andkit.lifecycle.coroutines

import com.anymore.andkit.lifecycle.ComponentLifecycle
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by lym on 2020/11/12.
 */
private val IgnoreErrorHandler = CoroutineExceptionHandler { _, _ -> }

fun ComponentLifecycle.launch(
    onPrepare: (suspend () -> Unit)? = null,
    onFailure: (suspend (t: Throwable) -> Unit)? = null,
    onFinal: (suspend () -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
): Job? {
    if (hasDestroyed) {
        Timber.w("ComponentLifecycle is Destroyed!")
        return null
    }
    val job = mCoroutineScope.launch(IgnoreErrorHandler) InnerLaunch@{
        try {
            onPrepare?.invoke()
            if (hasDestroyed) {
                Timber.w("ComponentLifecycle is Destroyed!")
                return@InnerLaunch
            }
            block()
        } catch (e: Exception) {
            Timber.e(e, "ComponentLifecycle.launch ERROR:")
            if (hasDestroyed) {
                Timber.w("ComponentLifecycle is Destroyed!")
                return@InnerLaunch
            }
            onFailure?.invoke(e)
        } finally {
            if (hasDestroyed) {
                Timber.w("ComponentLifecycle is Destroyed!")
                return@InnerLaunch
            }
            onFinal?.invoke()
        }
    }
    val key = block.javaClass.name
    mCoroutineScope.putTask(key, job)
    return job
}

suspend fun <T> bg(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

suspend fun <T> ComponentLifecycle.async(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return mCoroutineScope.async { block() }
}