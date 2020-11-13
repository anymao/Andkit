package com.anymore.andkit.lifecycle

import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.anymore.andkit.lifecycle.common.EXTRA
import com.anymore.andkit.lifecycle.common.putBundle
import com.anymore.andkit.lifecycle.common.uniqueTag
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

fun ComponentLifecycle.showFragment(
    fragment: Fragment,
    extra: Parcelable? = null,
    extraName: String = EXTRA,
    containerId: Int = android.R.id.content,
    tag: String? = null,
    addToBackStack: Boolean = true
): Fragment {
    val fm = mFragmentManager
    val mTag = tag ?: fragment.uniqueTag
    val f = (fm.findFragmentByTag(mTag) ?: fragment).putBundle(extra, extraName)
    fm.beginTransaction()
        .replace(containerId, f, mTag).let {
            if (addToBackStack) {
                it.addToBackStack(null)
            } else {
                it
            }
        }.commitAllowingStateLoss()
    return f
}

fun ComponentLifecycle.addNoViewFragment(fragment: Fragment, tag: String? = null) {
    mFragmentManager.beginTransaction()
        .add(fragment, tag ?: fragment.javaClass.name)
        .commitAllowingStateLoss()
}