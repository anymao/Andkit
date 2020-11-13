package com.anymore.andkit.lifecycle.activity

import androidx.appcompat.app.AppCompatActivity
import com.anymore.andkit.lifecycle.coroutines.AndkitLifecycleCoroutineScope

/**
 * Created by lym on 2020/11/12.
 */
abstract class AndkitActivity:AppCompatActivity(),IActivity{
    override val mContext by lazy { this }
    override val mLifecycleOwner by lazy { this }
    override val mFragmentManager by lazy { supportFragmentManager }
    override val mCoroutineScope by lazy { AndkitLifecycleCoroutineScope(this) }
    override val hasDestroyed get() = isDestroyed
}