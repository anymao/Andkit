package com.anymore.andkit.lifecycle.dialog

import android.app.Activity
import androidx.fragment.app.DialogFragment
import com.anymore.andkit.lifecycle.ComponentLifecycle
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope

/**
 * Created by anymore on 2021/1/31.
 */
abstract class AndkitDialogFragment : DialogFragment(), ComponentLifecycle {
    override val activity: Activity
        get() = requireActivity()
    override val mContext by lazy { requireContext() }
    override val mLifecycleOwner by lazy { this }
    override val mFragmentManager by lazy { childFragmentManager }
    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }
    override val hasDestroyed: Boolean
        get() = isDetached
    override val useEventBus = false
}