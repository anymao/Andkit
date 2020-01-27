package com.anymore.wanandroid.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.dagger.key.ViewModelKey
import com.anymore.wanandroid.mvvm.viewmodel.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by liuyuanmao on 2020/1/19.
 */
@Module
interface RegisterViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel
}