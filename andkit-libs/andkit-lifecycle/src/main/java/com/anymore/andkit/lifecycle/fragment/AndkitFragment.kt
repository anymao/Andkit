package com.anymore.andkit.lifecycle.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anymore.andkit.lifecycle.ComponentLifecycle
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope
import com.anymore.andkit.lifecycle.eventbus.EventBusManager

/**
 * Created by lym on 2020/11/12.
 */
abstract class AndkitFragment : Fragment(), ComponentLifecycle {

    override val activity: Activity
        get() = requireActivity()

    override val mContext by lazy { requireContext() }

    override val mLifecycleOwner by lazy { this }

    override val mFragmentManager by lazy { childFragmentManager }

    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }

    override val hasDestroyed get() = isDetached

    override val useEventBus = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus) {
            EventBusManager.register(this)
        }
    }

    override fun onDestroyView() {
        if (useEventBus) {
            EventBusManager.unregister(this)
        }
        super.onDestroyView()
    }
}