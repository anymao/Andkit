package com.anymore.wanandroid

import android.content.Context
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.annotations.Kiss
import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.di.component.DaggerMineComponent

/**
 * Created by anymore on 2020/1/29.
 */
@Kiss(MineApplicationWrapper::class,priority = 99)
class MineApplication:AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerMineComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }
}