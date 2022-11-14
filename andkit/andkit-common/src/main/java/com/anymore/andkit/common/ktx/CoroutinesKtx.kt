package com.anymore.andkit.common.ktx

import com.anymore.andkit.common.ktx.CoroutinesHandler.GlobalExceptionHandler
import com.anymore.andkit.common.ktx.CoroutinesHandler.defaultOnFailed
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.EmptyCoroutineContext

object CoroutinesHandler {
    @JvmStatic
    private val gehSet = AtomicBoolean(false)

    //我们应该设置一个全局兜底的异常处理器，实际逻辑应该是上报异常统计里面去
    @JvmStatic
    var GlobalExceptionHandler = CoroutineExceptionHandler { _, _ -> }
        set(value) {
            if (gehSet.compareAndSet(false, true)) {
                field = value
            } else {
                throw IllegalAccessException("you can just set GlobalExceptionHandler once!")
            }
        }

    /**
     * 默认的业务异常处理：不处理
     */
    @JvmStatic
    var defaultOnFailed: suspend CoroutineScope.(Throwable?) -> Boolean = { false }

}

suspend fun <T> bg(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

suspend fun <T> ui(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main.immediate, block)
}


/**
 * [onBefore]协程体执行前的处理逻辑，比如：showLoading
 * [block]你的协程体
 * [onAfter]协程体执行后的处理逻辑，比如showContentView
 * [onFailed]如果协程体出现异常，则异常会交给[onFailed]处理
 * [onFinal]执行完协程体(无论成功还是失败)最后的操作，比如hideLoading
 */
fun CoroutineScope.safeLaunch(
    onBefore: (suspend CoroutineScope.() -> Unit)? = null,
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = defaultOnFailed,
    onFinal: (suspend CoroutineScope.() -> Unit)? = null,
    block: (suspend CoroutineScope.() -> Unit)
): Job {
    return launch(EmptyCoroutineContext + GlobalExceptionHandler) {
        try {
            onBefore?.invoke(this)
            block()
            onAfter?.invoke(this)
        } catch (e: Throwable) {
            var handled = false
            if (!handled) {
                handled = onFailed(this, e)
            }
            // onFailed不处理
            // 则会交给全局的GlobalExceptionHandler来处理
            if (!handled) {
                throw e
            }
        } finally {
            onFinal?.invoke(this)
        }
    }
}
