package com.anymore.wanandroid.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.lifecycle.key.ViewModelKey
import com.anymore.wanandroid.mvvm.viewmodel.HomePageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by anymore on 2020/1/25.
 */
@Module
interface HomePageViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    fun bindHomePageViewModel(viewModel: HomePageViewModel):ViewModel
}