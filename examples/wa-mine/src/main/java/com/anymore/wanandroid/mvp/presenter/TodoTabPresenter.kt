package com.anymore.wanandroid.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.mvp.contract.TodoTabContract
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/29.
 */
class TodoTabPresenter @Inject constructor(
    application: Application,
    mView: TodoTabContract.ITodoTabView,
    private val mModel: TodoTabContract.ITodoTabModel
) :
    BasePresenter<TodoTabContract.ITodoTabView>(application, mView),
    TodoTabContract.ITodoTabPresenter {

}