package com.anymore.andkit.mvvm.di

import androidx.lifecycle.ViewModelProvider
import com.anymore.andkit.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelFactoryModule {

    @Singleton
    @Binds
    fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}