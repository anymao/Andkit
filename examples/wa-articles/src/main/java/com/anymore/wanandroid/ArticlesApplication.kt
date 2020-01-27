package com.anymore.wanandroid

import android.content.Context
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.annotations.Kiss
import com.anymore.andkit.lifecycle.di.module.ApplicationModule
import com.anymore.wanandroid.di.component.DaggerArticlesModuleComponent

/**
 * Created by anymore on 2020/1/25.
 */
@Kiss(value = ArticlesApplicationWrapper::class, priority = 100)
class ArticlesApplication : AndkitApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerArticlesModuleComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }
}