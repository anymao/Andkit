package com.anymore.wanandroid.web.view

import android.os.Bundle
import com.anymore.andkit.core.base.BaseActivity
import com.anymore.wanandroid.frame.ktx.go

/**
 * Created by anymore on 2022/4/11.
 */
class SchemaDispatchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        if (data != null) {
            go(data)
        }
        finish()
    }
}