package com.anymore.wanandroid.di.module

import com.anymore.wanandroid.mvp.contract.*
import com.anymore.wanandroid.mvp.view.activity.TodoActivity
import com.anymore.wanandroid.mvp.view.activity.TodoTabActivity
import com.anymore.wanandroid.mvp.view.fragment.CollectedArticlesFragment
import com.anymore.wanandroid.mvp.view.fragment.MineFragment
import com.anymore.wanandroid.mvp.view.fragment.TodoListFragment
import dagger.Binds
import dagger.Module

/**
 * Created by anymore on 2020/1/29.
 */
@Module
abstract class ViewsModule{

    @Binds
    abstract fun bindTodoTabActivity(activity: TodoTabActivity):TodoTabContract.ITodoTabView

    @Binds
    abstract fun bindTodoListFragment(fragment:TodoListFragment):TodoListContract.ITodoListView

    @Binds
    abstract fun bindTodoActivity(activity:TodoActivity):TodoContract.ITodoView

    @Binds
    abstract fun bindCollectedArticlesFragment(fragment: CollectedArticlesFragment):CollectedArticlesContract.ICollectedArticlesView

    @Binds
    abstract fun bindMineFragment(fragment: MineFragment):MineContract.IMineView
}