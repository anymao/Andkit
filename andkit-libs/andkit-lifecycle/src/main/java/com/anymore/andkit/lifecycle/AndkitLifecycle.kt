package com.anymore.andkit.lifecycle

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by lym on 2020/11/12.
 */
fun ComponentLifecycle.rxPermissions(): RxPermissions = when (this) {
    is Fragment -> RxPermissions(this)
    is FragmentActivity -> RxPermissions(this)
    else -> throw UnsupportedOperationException("unsupported class ${this.javaClass} which implements LifecycleHolder")
}

fun ComponentLifecycle.checkPermissions(
    vararg permissions: String,
    processEvent: ((List<Permission>) -> Unit)? = null,
    refusedEvent: (() -> Boolean)? = null,
    grantedEvent: (() -> Unit)? = null
) {
    rxPermissions().apply {
        requestEach(*permissions).buffer(permissions.size).subscribe { list ->
            processEvent?.invoke(list)
            val permission = Permission(list)
            when {
                permission.granted -> {
                    grantedEvent?.invoke()
                }
                permission.shouldShowRequestPermissionRationale -> {
                    if (refusedEvent?.invoke() != true) {
                        Toast.makeText(mContext, "权限被拒绝", Toast.LENGTH_SHORT).show()
                    }

                }
                else -> {
                    if (refusedEvent?.invoke() != true) {
                        Toast.makeText(mContext, "权限被禁用，请到应用设置里面开启相应权限", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}