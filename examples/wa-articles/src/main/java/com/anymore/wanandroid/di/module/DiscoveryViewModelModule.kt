package com.anymore.wanandroid.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.dagger.key.ViewModelKey
import com.anymore.wanandroid.mvvm.viewmodel.DiscoveryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by anymore on 2020/1/27.
 */
@Module
abstract class DiscoveryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DiscoveryViewModel::class)
    abstract fun bindDiscoveryViewModel(viewModel: DiscoveryViewModel): ViewModel
}