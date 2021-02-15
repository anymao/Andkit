package com.anymore.andkit.lifecycle.activity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.anymore.andkit.lifecycle.ComponentLifecycle
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope
import com.anymore.andkit.lifecycle.eventbus.EventBusManager

/**
 * Created by lym on 2020/11/12.
 */
abstract class AndkitActivity : AppCompatActivity(), ComponentLifecycle {

    override val activity: Activity get() = this

    override val mContext get() = this

    override val mLifecycleOwner get() = this

    override val mFragmentManager get() = supportFragmentManager

    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }

    override val hasDestroyed get() = isDestroyed

    override val useEventBus = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (useEventBus) {
            EventBusManager.register(this)
        }
    }

    override fun onDestroy() {
        if (useEventBus) {
            EventBusManager.unregister(this)
        }
        super.onDestroy()
    }

}