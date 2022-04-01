package com.anymore.andkit.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * Created by anymore on 2022/3/29.
 */
open class AndkitApplication: Application() {
    private val delegate = ApplicationDelegateManager
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        delegate.attachBaseContext(this,base)
    }

    override fun onCreate() {
        super.onCreate()
        delegate.onCreate(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate.onConfigurationChanged(this,newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        delegate.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        delegate.onTrimMemory(this,level)
    }

    override fun onTerminate() {
        super.onTerminate()
        delegate.onTerminate(this)
    }

}