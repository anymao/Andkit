package com.anymore.andkit.lifecycle.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by anymore on 2020/1/26.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplication():Application = application
}