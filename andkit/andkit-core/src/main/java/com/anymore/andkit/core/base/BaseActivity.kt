package com.anymore.andkit.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anymore.andkit.core.loading.DialogLoadingDelegateImpl
import org.greenrobot.eventbus.EventBus

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseActivity : AppCompatActivity(), ComponentContext {

    override val ccContext get() = this
    override val ccActivity get() = this
    override val ccCoroutineScope get() = lifecycleScope
    override val ccFragmentManager get() = supportFragmentManager
    override val ccDestroyed get() = isDestroyed
    override val delegate by lazy { DialogLoadingDelegateImpl(this) }

    open val useEventBus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onDestroy() {
        if (useEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }
}