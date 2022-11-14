@file:Suppress("DEPRECATION")

package com.anymore.andkit.common.ktx

import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.anymore.andkit.common.util.RequestFragment
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

fun FragmentActivity.startActivityForResult(
    intent: Intent,
    requestCode: Int,
    onActivityResult: ((Int, Int, Intent?) -> Unit)
) {
    val manager = supportFragmentManager
    var fragment = manager.findFragmentByTag(RequestFragment.FRAGMENT_TAG) as? RequestFragment
    if (fragment == null) {
        tryOrNothing {
            manager.beginTransaction()
                .add(RequestFragment().apply { fragment = this }, RequestFragment.FRAGMENT_TAG)
                .commitAllowingStateLoss()
            manager.executePendingTransactions()
        }
    }
    fragment?.onActivityResult = onActivityResult
    fragment?.startActivityForResult(intent, requestCode)
}

private val startActivityRequestCode = AtomicInteger(0)//Must be between 0 and 65535

fun FragmentActivity.startActivityForResult(
    intent: Intent,
    onActivityResult: (Int, Intent?) -> Unit
) {
    val currentRequestCode = startActivityRequestCode.get()
    startActivityRequestCode.set((currentRequestCode+1)%65536)
    startActivityForResult(intent, currentRequestCode) { requestCode, resultCode, data ->
        if (requestCode == currentRequestCode) {
            onActivityResult(resultCode, data)
        }
    }
}


fun FragmentActivity.requestPermissions(
    requestCode: Int,
    permissions: Array<out String>,
    onRequestPermissionsResult: ((Int, Array<out String>, IntArray) -> Unit)
) {
    val manager = supportFragmentManager
    var fragment = manager.findFragmentByTag(RequestFragment.FRAGMENT_TAG) as? RequestFragment
    if (fragment == null) {
        tryOrNothing {
            manager.beginTransaction()
                .add(RequestFragment().apply { fragment = this }, RequestFragment.FRAGMENT_TAG)
                .commitAllowingStateLoss()
            manager.executePendingTransactions()
        }
    }
    fragment?.onRequestPermissionsResult = onRequestPermissionsResult
    fragment?.requestPermissions(permissions, requestCode)
}

private val permissionRequestCode = AtomicInteger(0)//Must be between 0 and 65535

fun FragmentActivity.requestPermissions(
    permissions: Array<out String>,
    onPermissionsDenied: ((Array<out String>) -> Unit)?,
    onPermissionsGrand: (() -> Unit)
) {
    //找出未授权的权限
    val unGrantedPermissions = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) !=
                PackageManager.PERMISSION_GRANTED
    }
    if (unGrantedPermissions.isNullOrEmpty()) {
        Timber.v("拥有所有动态权限")
        onPermissionsGrand()
    } else {
        val currentRequestCode = permissionRequestCode.get()
        permissionRequestCode.set((currentRequestCode+1)%65536)
        val result: ((Int, Array<out String>, IntArray) -> Unit) =
            { requestCode, permissions1, grantResults ->
                if (requestCode == currentRequestCode) {
                    val unGranted = permissions1.filterIndexed { index, _ ->
                        grantResults[index] != PackageManager.PERMISSION_GRANTED
                    }
                    if (unGranted.isNullOrEmpty()) {
                        onPermissionsGrand()
                    } else {
                        onPermissionsDenied?.invoke(unGranted.toTypedArray())
                    }
                }

            }
        Timber.w("请求动态权限：$unGrantedPermissions")
        requestPermissions(currentRequestCode, unGrantedPermissions.toTypedArray(), result)
    }
}