package com.anymore.andkit.lifecycle

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope

/**
 * Activity和Fragment的共同接口，
 * Created by lym on 2020/11/12.
 */
interface ComponentLifecycle {

    val activity: Activity

    val mContext: Context

    val mLifecycleOwner: LifecycleOwner

    val mFragmentManager: FragmentManager

    val mCoroutineScope: AndkitLifecycleCoroutineScope

    val hasDestroyed: Boolean

    val useEventBus: Boolean
}
