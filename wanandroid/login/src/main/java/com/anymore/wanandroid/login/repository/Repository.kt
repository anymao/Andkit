package com.anymore.wanandroid.login.repository

import android.content.Context
import com.anymore.wanandroid.frame.ktx.route
import com.anymore.wanandroid.frame.router.WanAndroidRouter.login
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by anymore on 2022/4/7.
 */
@Module
@InstallIn(SingletonComponent::class)
object Repository {

    @Singleton
    @Provides
    fun provideWanAndroidOnLogOutAction(@ApplicationContext context: Context): () -> Unit {
        return {
            route(login).start(context)
        }
    }
}