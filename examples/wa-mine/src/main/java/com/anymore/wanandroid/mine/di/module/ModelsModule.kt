package com.anymore.wanandroid.mine.di.module

import com.anymore.wanandroid.mine.mvp.contract.CollectedArticlesContract
import com.anymore.wanandroid.mine.mvp.contract.MineContract
import com.anymore.wanandroid.mine.mvp.contract.TodoContract
import com.anymore.wanandroid.mine.mvp.contract.TodoTabContract
import com.anymore.wanandroid.mine.mvp.model.CollectedArticlesModel
import com.anymore.wanandroid.mine.mvp.model.MineModel
import com.anymore.wanandroid.mine.mvp.model.TodoModel
import com.anymore.wanandroid.mine.mvp.model.TodoTabModel
import dagger.Binds
import dagger.Module

/**
 * Created by anymore on 2020/1/29.
 */
@Module
abstract class ModelsModule {

    @Binds
    abstract fun bindTodoTabModel(model: TodoTabModel): TodoTabContract.ITodoTabModel


    @Binds
    abstract fun bindTodoModel(model: TodoModel): TodoContract.ITodoModel

    @Binds
    abstract fun bindCollectedArticlesModel(model: CollectedArticlesModel): CollectedArticlesContract.ICollectedArticlesModel

    @Binds
    abstract fun bindMineModel(model: MineModel): MineContract.IMineModel
}