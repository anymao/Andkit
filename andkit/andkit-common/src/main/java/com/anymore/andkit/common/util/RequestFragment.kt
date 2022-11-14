package com.anymore.andkit.common.util

import android.content.Intent
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
@RestrictTo(RestrictTo.Scope.LIBRARY)
class RequestFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "com.anymore.andkit.common.util.RequestFragment"
    }

    var onActivityResult: ((Int, Int, Intent?) -> Unit)? = null
    var onRequestPermissionsResult: ((Int, Array<out String>, IntArray) -> Unit)? = null

    init {
        retainInstance = true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult?.invoke(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult?.invoke(requestCode, permissions, grantResults)
    }

}