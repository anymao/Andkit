package com.anymore.wanandroid.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.lifecycle.key.ViewModelKey
import com.anymore.wanandroid.mvvm.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by liuyuanmao on 2020/1/18.
 */
@Module
interface LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}