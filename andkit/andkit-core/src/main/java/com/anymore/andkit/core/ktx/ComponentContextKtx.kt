package com.anymore.andkit.core.ktx

import android.app.Activity
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.Size
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.anymore.andkit.common.ktx.*
import com.anymore.andkit.common.ktx.CoroutinesHandler.defaultOnFailed
import com.anymore.andkit.common.loader.LoadCallback
import com.anymore.andkit.core.base.ComponentContext
import com.anymore.andkit.core.ktx.ComponentContextExceptionHandler.defaultOnBizFailed
import com.anymore.andkit.rpc.ErrorResponseException
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by anymore on 2022/3/30.
 */

object ComponentContextExceptionHandler {
    //我们应该设置一个全局兜底的异常处理器，实际逻辑应该是上报异常统计里面去
    @JvmStatic
    fun setComponentContextCoroutineExceptionHandler(handler: ((ComponentContext?, CoroutineContext, Throwable) -> Unit)) {
        ComponentContextCoroutineExceptionHandler.handler = handler
        CoroutinesHandler.GlobalExceptionHandler = ComponentContextCoroutineExceptionHandler()
    }

    /**
     * 默认的业务异常处理：不处理
     */
    @JvmStatic
    var defaultOnFailed: suspend CoroutineScope.(Throwable?) -> Boolean = {
        Timber.e(it, "DefaultOnFailed:")
        false
    }
        set(value) {
            field = value
            CoroutinesHandler.defaultOnFailed = value
        }

    /**
     * 默认的业务异常处理:将 errmsg内容toast出来
     */
    val defaultOnBizFailed: suspend CoroutineScope.(Long?, String?) -> Boolean =
        { code, message ->
            Timber.e("DefaultBizFailed:[code:$code,message:$message]")
            toastFailed(message)
            true
        }
}

private class ComponentContextCoroutineExceptionHandler(private val cc: ComponentContext? = null) :
    AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    companion object {
        private val isHandlerSet = AtomicBoolean(false)
        var handler: (ComponentContext?, CoroutineContext, Throwable) -> Unit = { _, _, _ -> }
            set(value) {
                if (isHandlerSet.compareAndSet(false, true)) {
                    field = value
                } else {
                    throw IllegalAccessException("you can just set the handler only once!")
                }
            }
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        handler(cc, context, exception)
    }

}


/**
 * [onBefore]协程体执行前的处理逻辑，比如：showLoading
 * [block]你的协程体
 * [onAfter]协程体执行后的处理逻辑，比如showContentView
 * [onBizFailed]如果协程执行过程中出现API业务异常[ErrorResponseException],则[onBizFailed]会进行处理
 * [onFailed]如果协程体出现的是非API业务异常 或者 是API业务异常但是[onBizFailed]没有处理(return false)，则异常会交给[onFailed]处理
 * [onFinal]执行完协程体(无论成功还是失败)最后的操作，比如hideLoading
 *
 *
 * 我们希望发起协程在view层里。这样我们就无须借助ViewModel里面的loading,toast,failedToast等这几个LiveData来实现错误信息以及loading信息的传递
 */
fun ComponentContext.launch(
    onBefore: (suspend CoroutineScope.() -> Unit)? = null,
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onBizFailed: (suspend CoroutineScope.(Long?, String?) -> Boolean) = defaultOnBizFailed,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = defaultOnFailed,
    onFinal: (suspend CoroutineScope.() -> Unit)? = null,
    block: (suspend CoroutineScope.() -> Unit)
): Job {
    val handler = ComponentContextCoroutineExceptionHandler(this)
    return ccCoroutineScope.launch(EmptyCoroutineContext + handler) {
        try {
            onBefore?.invoke(this)
            block()
            onAfter?.invoke(this)
        } catch (e: Throwable) {
            var handled = false
            if (e is ErrorResponseException) {
                handled = onBizFailed(this, e.code, e.message)
            }
            if (!handled) {
                handled = onFailed(this, e)
            }
            // onBizFailed和onFailed都不处理
            // 则会交给全局的ComponentContextCoroutineExceptionHandler来处理
            if (!handled) {
                throw e
            }
        } finally {
            onFinal?.invoke(this)
        }
    }
}

/**
 * 发起协程并且使用loading
 * 默认使用效果：
 * 1.协程调用前，showLoading
 * 2.执行协程体
 * 3.如果协程体异常：
 *     1)：如果异常是接口请求异常，则会被onBizFailed处理，toastFailed(errmsg)
 *     2)：如果异常是非接口异常，则会被onFailed处理，默认打印出来
 * 4.hideLoading
 */
fun ComponentContext.launchWithLoading(
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onBizFailed: (suspend CoroutineScope.(Long?, String?) -> Boolean) = defaultOnBizFailed,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = defaultOnFailed,
    block: suspend CoroutineScope.() -> Unit
) = launch(
    onBefore = { showLoading() },
    onAfter = onAfter,
    onBizFailed = onBizFailed,
    onFailed = onFailed,
    onFinal = { hideLoading() },
    block = block
)

fun ComponentContext.launch(
    callback: LoadCallback? = null,
    block: (suspend CoroutineScope.() -> Unit)
) = launch(
    onBefore = { callback?.onPrepare() },
    onAfter = { callback?.onSuccess() },
    //业务异常不处理，交给onFailed处理
    onBizFailed = { _, _ -> false },
    onFailed = { throwable: Throwable? ->
        Timber.e(throwable, "LoadCallback handle the Exception!")
        callback?.onFailed(throwable).orFalse()
    },
    onFinal = {
        callback?.onFinal()
    }, block = block
)

/**
 * show一个Fragment出来.
 * 如果你现在在一个Fragment或者在Activity中，如果需要展示一个新的Fragment并且想这个Fragment的行为像Activity一样具有栈的特性
 * 那么可以使用这个方法
 * 什么时候我们考虑用这个方式呢？例如我们想新起一个页面，这个页面有一个或者多个回调事件，我们在当前页面需要监听这些事件，
 * 如果使用Activity来实现这个方案会有些麻烦，如果使用ViewModel来通信，也可以，但是不是最简方案。
 * <pre>
 *      val fragment = AFragment()
 *      fragment.onClick1 = {
 *          println("onClick1")
 *      }
 *      fragment.onClick2 = {
 *          println("onClick2")
 *      }
 *      showFragment(enableStack = true,fragment = fragment)
 * </pre>
 */
fun ComponentContext.showFragment(
    containerId: Int = android.R.id.content,
    enableStack: Boolean = false,
    tag: String? = null,
    fragment: Fragment
): Fragment {
    return tryOr(fragment) {
        val realTag = tag ?: fragment.className
        val f = ccFragmentManager.findFragmentByTag(realTag) ?: fragment
        ccFragmentManager.beginTransaction()
            .replace(containerId, f, tag ?: fragment.className).also {
                if (enableStack) {
                    it.addToBackStack(null)
                }
            }.commitAllowingStateLoss()
        f
    }
}

/**
 * 从View获取到ComponentContext
 * 如果View属于Fragment，那么这个ComponentContext实际上就是Fragment
 * 如果View属于Activity，那么这个ComponentContext实际上就是Activity
 * 这样的话，无论我们处于Activity，Fragment，还是View中，都能发起协程
 * 这样如果我们发起协程，将会更安全（因为此协程的scope更加明确，是伴随着生命周期的）
 * */
fun View.findComponentContext(): ComponentContext? {
    val lifecycleOwner: LifecycleOwner? = findViewTreeLifecycleOwner()
    if (lifecycleOwner is ComponentContext) {
        return lifecycleOwner
    }
    return null
}

/**
 * View级别安全发起协程
 * 如果View是处于[ComponentContext]中（即View所在的Activity或者是Fragment实现了ComponentContext）
 * 则View最终是使用[ComponentContext]发起协程
 * 如果View是处于[ComponentActivity]或者[Fragment]中，则最终是使用[safeLaunch]发起协程
 * 如果View找不到对应的[LifecycleOwner]则使用Application级别的[LifecycleOwner]发起协程
 * 如果你使用自定义View使用协程处理业务的时候，可以考虑使用此方法，如果你处于Activity或者Fragment中，
 * 就没有必要使用此方法
 */
@CheckResult(suggest = "如果返回的是Application级别的协程作用域，则需要手动将返回的Job在适当的生命周期取消掉")
fun View.launch(
    onBefore: (suspend CoroutineScope.() -> Unit)? = null,
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onBizFailed: (suspend CoroutineScope.(Long?, String?) -> Boolean) = defaultOnBizFailed,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = defaultOnFailed,
    onFinal: (suspend CoroutineScope.() -> Unit)? = null,
    block: (suspend CoroutineScope.() -> Unit)
): Job {
    val cc = findViewTreeLifecycleOwner()
    return if (cc != null) {
        if (cc is ComponentContext) {
            cc.launch(onBefore, onAfter, onBizFailed, onFailed, onFinal, block)
        } else {
            cc.lifecycleScope.safeLaunch(onBefore, onAfter, onFailed, onFinal, block)
        }
    } else {
        @Suppress("OPT_IN_USAGE")
        GlobalScope.safeLaunch(
            onBefore,
            onAfter,
            onFailed,
            onFinal,
            block
        )
    }
}


/**
 * 兼容性启动协程
 */
fun Activity.launchCompatible(
    onBefore: (suspend CoroutineScope.() -> Unit)? = null,
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = defaultOnFailed,
    onFinal: (suspend CoroutineScope.() -> Unit)? = null,
    block: (suspend CoroutineScope.() -> Unit)
): Job {
    return when (this) {
        is ComponentContext -> {
            launch(onBefore, onAfter, { _, _ -> false }, onFailed, onFinal, block)
        }
        is LifecycleOwner -> {
            lifecycleScope.safeLaunch(onBefore, onAfter, onFailed, onFinal, block)
        }
        else -> {
            @Suppress("OPT_IN_USAGE")
            GlobalScope.safeLaunch(
                onBefore,
                onAfter,
                onFailed,
                onFinal,
                block
            )
        }
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

fun View.requireComponentContext(lazyMessage: () -> Any = { "the view must inflate in Activity of Fragment impl ComponentContext " }) =
    requireNotNull(findComponentContext(), lazyMessage)