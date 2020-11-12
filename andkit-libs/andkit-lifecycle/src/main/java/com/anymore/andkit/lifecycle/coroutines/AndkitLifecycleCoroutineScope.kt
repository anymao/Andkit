package com.anymore.andkit.lifecycle.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

/**
 * Created by lym on 2020/11/12.
 */
class AndkitLifecycleCoroutineScope(lifecycleOwner: LifecycleOwner) : CoroutineScope {
    private val mTaskPool by lazy { ConcurrentHashMap<String, Job>() }
    val lifecycle = lifecycleOwner.lifecycle
    override val coroutineContext: CoroutineContext
        get() = lifecycle.coroutineScope.coroutineContext

    fun launchWhenCreated(block: suspend CoroutineScope.() -> Unit) =
        lifecycle.coroutineScope.launchWhenCreated(block)

    fun launchWhenStarted(block: suspend CoroutineScope.() -> Unit) =
        lifecycle.coroutineScope.launchWhenStarted(block)

    fun launchWhenResumed(block: suspend CoroutineScope.() -> Unit) =
        lifecycle.coroutineScope.launchWhenResumed(block)

    fun getTask(key: String): Job? = mTaskPool[key]

    fun putTask(key: String, job: Job) {
        mTaskPool[key] = job
    }
}