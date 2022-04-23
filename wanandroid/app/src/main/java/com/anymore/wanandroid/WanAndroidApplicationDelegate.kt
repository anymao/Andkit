package com.anymore.wanandroid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.anymore.andkit.core.ApplicationDelegate
import com.anymore.auto.AutoService
import com.didi.drouter.api.DRouter
import com.didi.drouter.utils.RouterLogger
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
            RouterLogger.setPrinter(object : RouterLogger.ILogPrinter {
                override fun d(TAG: String?, content: String?) {
                    Timber.tag(TAG).d(content)
                }

                override fun w(TAG: String?, content: String?) {
                    Timber.tag(TAG).w(content)
                }

                override fun e(TAG: String?, content: String?) {
                    Timber.tag(TAG).e(content)
                }
            })
        }
        RouterLogger.setEnable(BuildConfig.DEBUG)
        DRouter.init(application)
    }
}