package com.anymore.andkit.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.anymore.auto.ServiceLoader

/**
 * Created by anymore on 2022/3/29.
 */
internal object ApplicationDelegateManager : ApplicationDelegate {

    private val delegates = ServiceLoader.load<ApplicationDelegate>()

    override fun attachBaseContext(application: Application, base: Context?) {
        delegates.forEach {
            it.attachBaseContext(application, base)
        }
    }

    override fun onCreate(application: Application) {
        delegates.forEach {
            it.onCreate(application)
        }
    }

    override fun onConfigurationChanged(application: Application, newConfig: Configuration) {
        delegates.forEach {
            it.onConfigurationChanged(application, newConfig)
        }
    }

    override fun onLowMemory(application: Application) {
        delegates.forEach {
            it.onLowMemory(application)
        }
    }

    override fun onTrimMemory(application: Application, level: Int) {
        delegates.forEach {
            it.onTrimMemory(application, level)
        }
    }

    override fun onTerminate(application: Application) {
        delegates.forEach {
            it.onTerminate(application)
        }
    }
}