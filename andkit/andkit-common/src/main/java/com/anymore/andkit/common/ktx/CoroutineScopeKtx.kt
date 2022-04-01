package com.anymore.andkit.common.ktx

import com.anymore.andkit.common.ktx.CoroutineExceptionHandler.defaultDoFailed
import com.anymore.andkit.common.ktx.CoroutineExceptionHandler.globalCoroutineExceptionHandler
import com.anymore.andkit.common.loader.LoadCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by anymore on 2022/3/29.
 */
internal object CoroutineExceptionHandler {
    var globalCoroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> }
    val defaultDoFailed: CoroutineScope.(Throwable) -> Boolean = { false }
}

internal fun CoroutineScope.launch(
    doBefore: (CoroutineScope.() -> Unit)? = null,
    doAfter: (CoroutineScope.() -> Unit)? = null,
    doFailed: (CoroutineScope.(Throwable) -> Boolean) = defaultDoFailed,
    doFinal: ((CoroutineScope.() -> Unit))? = null,
    block: CoroutineScope.() -> Unit
): Job {
    return launch(
        context = EmptyCoroutineContext + globalCoroutineExceptionHandler,
        start = CoroutineStart.DEFAULT
    ) {
        try {
            doBefore?.invoke(this)
            block(this)
            doAfter?.invoke(this)
        } catch (e: Throwable) {
            if (!doFailed(this, e)) {
                throw e
            }
        } finally {
            doFinal?.invoke(this)
        }
    }
}

internal fun CoroutineScope.launch(
    loadCallback: LoadCallback,
    block: CoroutineScope.() -> Unit
): Job {
    return launch(
        doBefore = { loadCallback.onPrepare() },
        doAfter = { loadCallback.onSuccess() },
        doFailed = { loadCallback.onFailed(it) },
        doFinal = { loadCallback.onFinal() },
        block = block
    )
}