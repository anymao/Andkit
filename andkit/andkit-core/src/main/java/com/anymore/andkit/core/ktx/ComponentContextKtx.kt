package com.anymore.andkit.core.ktx

import com.anymore.andkit.common.loader.LoadCallback
import com.anymore.andkit.core.base.ComponentContext
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by anymore on 2022/3/30.
 */

fun ComponentContext.launch(
    doBefore: (suspend CoroutineScope.() -> Unit)? = null,
    doAfter: (suspend CoroutineScope.() -> Unit)? = null,
    doFailed: (suspend CoroutineScope.(Throwable) -> Boolean) = {
        Timber.e(it)
        false
    },
    doFinal: ((suspend CoroutineScope.() -> Unit))? = null,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val handler = ComponentContextCoroutineExceptionHandler(this)
    return ccCoroutineScope.launch(
        context = EmptyCoroutineContext + handler,
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

fun ComponentContext.launch(
    loadCallback: LoadCallback,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(
        doBefore = { loadCallback.onPrepare() },
        doAfter = { loadCallback.onSuccess() },
        doFailed = { loadCallback.onFailed(it) },
        doFinal = { loadCallback.onFinal() },
        block = block
    )
}

fun ComponentContext.launchWithLoading(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(
        doBefore = { showLoading(null) },
        doFinal = { hideLoading() },
        block = block
    )
}


private class ComponentContextCoroutineExceptionHandler(private val cc: ComponentContext? = null) :
    AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    companion object {
        var handler: (ComponentContext?, CoroutineContext, Throwable) -> Unit = { _, _, _ -> }
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        handler.invoke(cc, context, exception)
    }

}