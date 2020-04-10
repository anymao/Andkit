package com.anymore.andkit.di.module

import android.app.Application
import com.anymore.andkit.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by liuyuanmao on 2020/1/16.
 */
@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application


    @Provides
    fun provideAppName(application: Application): String {
        return application.getString(R.string.app_name)
    }
}