package com.anymore.wanandroid.articles.di.module

import androidx.lifecycle.ViewModel
import com.anymore.andkit.dagger.key.ViewModelKey
import com.anymore.wanandroid.articles.mvvm.viewmodel.ArticlesSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by anymore on 2020/4/20.
 */
@Module
abstract class ArticlesSearchViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ArticlesSearchViewModel::class)
    abstract fun bindArticlesSearchViewModel(viewModel: ArticlesSearchViewModel): ViewModel
}