package com.anymore.andkit.lifecycle.application

import android.app.Application
import android.content.res.Configuration

/**
 * Created by liuyuanmao on 2019/3/11.
 */
interface IApplicationLifecycle{
    /**
     * invoke at [Application.attachBaseContext]
     */
    fun attachBaseContext()
    /**
     * invoke at [Application.onCreate]
     */
    fun onCreate()
    /**
     * invoke at [Application.onTerminate]
     */
    fun onTerminate()
    /**
     * invoke at [Application.onConfigurationChanged]
     */
    fun onConfigurationChanged(newConfig: Configuration){

    }
    /**
     * invoke at [Application.onLowMemory]
     */
    fun onLowMemory(){

    }
    /**
     * invoke at [Application.onTrimMemory]
     */
    fun onTrimMemory(level:Int){

    }
}