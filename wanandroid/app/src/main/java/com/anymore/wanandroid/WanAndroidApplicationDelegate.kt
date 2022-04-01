package com.anymore.wanandroid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.core.ApplicationDelegate
import com.anymore.andkit.core.BuildConfig
import com.google.auto.service.AutoService
import timber.log.Timber

/**
 * Created by anymore on 2022/3/30.
 */
@AutoService(ApplicationDelegate::class)
class WanAndroidApplicationDelegate : ApplicationDelegate {
    override fun attachBaseContext(application: Application, base: Context?) {
        super.attachBaseContext(application, base)
        MultiDex.install(application)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ARouter.init(application)
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
            ARouter.printStackTrace()
        }
        Timber.d("success")
    }
}