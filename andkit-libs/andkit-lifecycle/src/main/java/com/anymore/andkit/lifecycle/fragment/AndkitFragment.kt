package com.anymore.andkit.lifecycle.fragment

import androidx.fragment.app.Fragment
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope

/**
 * Created by lym on 2020/11/12.
 */
abstract class AndkitFragment : Fragment(), IFragment {
    override val mContext by lazy { requireContext() }
    override val mLifecycleOwner by lazy { this }
    override val mFragmentManager by lazy { childFragmentManager }
    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }
    override val hasDestroyed get() = isDetached
}