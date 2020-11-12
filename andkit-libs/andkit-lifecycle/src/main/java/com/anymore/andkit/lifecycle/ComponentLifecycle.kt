package com.anymore.andkit.lifecycle

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope

/**
 * Created by lym on 2020/11/12.
 */
interface ComponentLifecycle {
    val mContext: Context
    val mLifecycleOwner: LifecycleOwner
    val mCoroutineScope: AndkitLifecycleCoroutineScope
    val hasDestroyed: Boolean
}
