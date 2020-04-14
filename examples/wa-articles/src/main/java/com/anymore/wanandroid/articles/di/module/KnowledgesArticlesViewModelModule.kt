package com.anymore.wanandroid.articles.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.dagger.key.ViewModelKey
import com.anymore.wanandroid.articles.mvvm.viewmodel.KnowledgesArticlesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by anymore on 2020/1/27.
 */
@Module
abstract class KnowledgesArticlesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(KnowledgesArticlesViewModel::class)
    abstract fun bindKnowledgesArticlesViewModel(viewModel: KnowledgesArticlesViewModel): ViewModel
}