package com.anymore.wanandroid.browse

import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication

/**
 * Created by anymore on 2020/8/30.
 */
class BrowseApplication:AndkitApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }
}