package com.anymore.andkit.mvvm.di

import androidx.lifecycle.ViewModelProvider
import com.anymore.andkit.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}