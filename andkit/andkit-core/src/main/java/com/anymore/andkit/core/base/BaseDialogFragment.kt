package com.anymore.andkit.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.anymore.andkit.core.loading.DialogLoadingDelegateImpl
import org.greenrobot.eventbus.EventBus

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseDialogFragment : DialogFragment(), ComponentContext {
    override val ccContext get() = context
    override val ccActivity get() = activity
    override val ccCoroutineScope get() = lifecycleScope
    override val ccFragmentManager get() = parentFragmentManager
    override val ccDestroyed get() = isDetached
    override val delegate by lazy { DialogLoadingDelegateImpl(requireContext()) }

    open val useEventBus = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus) {
            EventBus.getDefault().register(this)
        }
        initView()
        getData()
    }

    open fun initView() {

    }

    open fun getData() {

    }

    override fun onDestroyView() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }
}