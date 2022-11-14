package com.anymore.andkit.common.ktx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anymore.andkit.common.loader.LoadCallback
import kotlinx.coroutines.CoroutineScope


fun ViewModel.launch(
    onBefore: (suspend CoroutineScope.() -> Unit)? = null,
    onAfter: (suspend CoroutineScope.() -> Unit)? = null,
    onFailed: (suspend CoroutineScope.(Throwable?) -> Boolean) = CoroutinesHandler.defaultOnFailed,
    onFinal: (suspend CoroutineScope.() -> Unit)? = null,
    block: (suspend CoroutineScope.() -> Unit)
) = viewModelScope.safeLaunch(
        onBefore = onBefore,
        onAfter = onAfter,
        onFinal = onFinal,
        onFailed = onFailed,
        block = block
    )


fun ViewModel.launch(
    callback: LoadCallback? = null,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.safeLaunch(
        onBefore = { callback?.onPrepare() },
        onAfter = { callback?.onSuccess() },
        onFailed = { callback?.onFailed(it) ?: false },
        onFinal = { callback?.onFinal() },
        block = block
    )
