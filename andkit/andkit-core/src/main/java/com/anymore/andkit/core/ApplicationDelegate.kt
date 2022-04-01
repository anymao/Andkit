package com.anymore.andkit.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * Created by anymore on 2022/3/29.
 */
interface ApplicationDelegate {

    fun attachBaseContext(application: Application, base: Context?) {

    }

    fun onCreate(application: Application) {

    }

    fun onConfigurationChanged(application: Application, newConfig: Configuration) {

    }

    fun onLowMemory(application: Application) {

    }

    fun onTrimMemory(application: Application, level: Int) {

    }

    fun onTerminate(application: Application) {

    }
}