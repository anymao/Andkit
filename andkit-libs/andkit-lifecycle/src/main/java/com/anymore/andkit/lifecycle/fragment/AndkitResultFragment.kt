package com.anymore.andkit.lifecycle.fragment

import android.os.Parcelable

/**
 * Created by lym on 2020/11/12.
 */
open class AndkitResultFragment<T: Parcelable> : AndkitFragment() {

    var listener:((T)->Unit)? = null

    fun finishWithResult(result: T){
        listener?.invoke(result)
        activity?.onBackPressed()
    }
}