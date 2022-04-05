package com.anymore.andkit.core.ktx

import android.view.View
import androidx.annotation.Size
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.anymore.andkit.common.ktx.uniqueTag
import com.anymore.andkit.common.loader.LoadCallback
import com.anymore.andkit.core.base.ComponentContext
import com.anymore.andkit.core.ktx.ComponentContextCoroutineExceptionHolder.defaultOnBizFailed
import com.anymore.andkit.core.ktx.ComponentContextCoroutineExceptionHolder.defaultOnFailed
import com.anymore.andkit.rpc.ErrorResponseException
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by anymore on 2022/3/30.
 */

object ComponentContextCoroutineExceptionHolder {
    val defaultOnBizFailed: suspend CoroutineScope.(ErrorResponseException) -> Boolean = { false }
    val defaultOnFailed: suspend CoroutineScope.(Throwable) -> Boolean = {
        Timber.e(it, "defaultOnFailed")
        false
    }
}


fun ComponentContext.launch(
    doBefore: (suspend CoroutineScope.() -> Unit)? = null,
    doAfter: (suspend CoroutineScope.() -> Unit)? = null,
    doBizFailed: (suspend CoroutineScope.(ErrorResponseException) -> Boolean) = defaultOnBizFailed,
    doFailed: (suspend CoroutineScope.(Throwable) -> Boolean) = defaultOnFailed,
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
            var handled = false
            if (e is ErrorResponseException) {
                handled = doBizFailed(this, e)
            }
            if (handled.not()) {
                handled = doFailed(this, e)
            }
            if (!handled) {
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


fun ComponentContext.showFragment(
    fragment: Fragment,
    containerId: Int = android.R.id.content,
    tag: String? = null,
    addToBackStack: Boolean = true,
    @Size(4)
    animations: IntArray? = null
): Fragment {
    val fm = ccFragmentManager
    val t = tag ?: fragment.uniqueTag
    val f = fm.findFragmentByTag(t) ?: fragment
    fm.beginTransaction().also {
        if (animations?.size == 4) {
            it.setCustomAnimations(
                animations[0],
                animations[1],
                animations[2],
                animations[3]
            )
        }
    }.replace(containerId, f, t).let {
        if (addToBackStack) {
            it.addToBackStack(null)
        } else {
            it
        }
    }.commitAllowingStateLoss()
    return f
}

fun View.findComponentContext(): ComponentContext? {
    val lifecycleOwner = findViewTreeLifecycleOwner() ?: return null
    return if (lifecycleOwner is ComponentContext) {
        lifecycleOwner
    } else {
        null
    }
}

fun View.requireComponentContext(lazyMessage: () -> Any = { "the view must inflate in Activity of Fragment impl ComponentContext " }) =
    requireNotNull(findComponentContext(), lazyMessage)