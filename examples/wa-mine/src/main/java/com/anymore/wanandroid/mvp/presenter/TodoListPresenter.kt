package com.anymore.wanandroid.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.common.ext.network2Main
import com.anymore.wanandroid.mvp.contract.TodoListContract
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/30.
 */
class TodoListPresenter @Inject constructor(
    application: Application,
    mView: TodoListContract.ITodoListView,
    private val mModel: TodoListContract.ITodoListModel
) :
    BasePresenter<TodoListContract.ITodoListView>(application, mView),
    TodoListContract.ITodoListPresenter {

    override fun loadTodoList(page: Int, type: Int) {
        val disposable = mModel.loadTodoList(page,type)
            .network2Main()
            .subscribeBy(
                onNext = {

                },
                onError = {
                    mView.showError(it.message?:"加载待办列表失败！")
                }
            )
        addDisposable(disposable)

    }


}