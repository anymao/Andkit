package com.anymore.wanandroid.common.ext

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import com.anymore.wanandroid.common.ContextProvider

/**
 * Created by anymore on 2020/11/15.
 */
private lateinit var mToast: Toast
private val mApplicationContext by lazy { ContextProvider.getApplicationContext() }

fun toast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(mApplicationContext.getString(stringId), duration)
}

@SuppressLint("ShowToast")
fun toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (message.isNullOrEmpty()) {
        return
    }
    runOnUiThread {
        if (!::mToast.isInitialized) {
            mToast = Toast.makeText(mApplicationContext, message, duration)
        } else {
            mToast.setText(message)
            mToast.duration = duration
        }
        mToast.show()
    }
}