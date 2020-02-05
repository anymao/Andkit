package com.anymore.wanandroid

import android.app.Application
import com.anymore.andkit.lifecycle.application.AbsApplicationWrapper
import io.flutter.view.FlutterMain

/**
 * Created by anymore on 2020/2/5.
 */
class MineApplicationWrapper(application: Application) : AbsApplicationWrapper(application) {
    override fun attachBaseContext() {

    }

    override fun onCreate() {
        FlutterMain.startInitialization(application)
    }

    override fun onTerminate() {

    }
}