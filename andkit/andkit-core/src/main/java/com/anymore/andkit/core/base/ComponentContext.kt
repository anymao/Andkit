package com.anymore.andkit.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner

/**
 * Created by anymore on 2022/3/29.
 */
interface ComponentContext : Loadable, LifecycleOwner {
    val ccContext: Context?
    val ccActivity: Activity?
    val ccCoroutineScope: LifecycleCoroutineScope
    val ccFragmentManager: FragmentManager
    val ccDestroyed: Boolean


    fun startActivity(intent: Intent?)

    fun startActivity(intent: Intent?, options: Bundle?)

    fun startActivityForResult(intent: Intent?, requestCode: Int)

    fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?)
}