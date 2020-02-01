package com.anymore.wanandroid.di.module

import com.anymore.wanandroid.mvp.contract.TodoListContract
import com.anymore.wanandroid.mvp.contract.TodoTabContract
import com.anymore.wanandroid.mvp.model.TodoListModel
import com.anymore.wanandroid.mvp.model.TodoTabModel
import dagger.Binds
import dagger.Module

/**
 * Created by anymore on 2020/1/29.
 */
@Module
abstract class ModelsModule {

    @Binds
    abstract fun bindTodoTabModel(model:TodoTabModel):TodoTabContract.ITodoTabModel

    @Binds
    abstract fun bindTodoListModel(model:TodoListModel):TodoListContract.ITodoListModel
}