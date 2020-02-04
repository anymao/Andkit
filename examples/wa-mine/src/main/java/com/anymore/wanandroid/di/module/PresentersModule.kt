package com.anymore.wanandroid.di.module

import com.anymore.wanandroid.mvp.contract.CollectedArticlesContract
import com.anymore.wanandroid.mvp.contract.TodoContract
import com.anymore.wanandroid.mvp.contract.TodoListContract
import com.anymore.wanandroid.mvp.contract.TodoTabContract
import com.anymore.wanandroid.mvp.presenter.CollectedArticlesPresenter
import com.anymore.wanandroid.mvp.presenter.TodoListPresenter
import com.anymore.wanandroid.mvp.presenter.TodoPresenter
import com.anymore.wanandroid.mvp.presenter.TodoTabPresenter
import dagger.Binds
import dagger.Module

/**
 * Created by anymore on 2020/1/29.
 */
@Module
abstract class PresentersModule {

    @Binds
    abstract fun bindTodoTabPresenter(presenter:TodoTabPresenter):TodoTabContract.ITodoTabPresenter

    @Binds
    abstract fun bindTodoListPresenter(presenter: TodoListPresenter):TodoListContract.ITodoListPresenter

    @Binds
    abstract fun bindTodoPresenter(presenter: TodoPresenter):TodoContract.ITodoPresenter

    @Binds
    abstract fun bindCollectedArticlesPresenter(presenter:CollectedArticlesPresenter):CollectedArticlesContract.ICollectedArticlesPresenter
}