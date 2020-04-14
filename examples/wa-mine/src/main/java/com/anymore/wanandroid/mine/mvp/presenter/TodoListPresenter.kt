package com.anymore.wanandroid.mine.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.common.ext.network2Main
import com.anymore.wanandroid.mine.mvp.contract.TodoContract
import com.anymore.wanandroid.mine.mvp.contract.TodoListContract
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/30.
 */
class TodoListPresenter @Inject constructor(
    application: Application,
    mView: TodoListContract.ITodoListView,
    private val mModel: TodoContract.ITodoModel
) :
    BasePresenter<TodoListContract.ITodoListView>(application, mView),
    TodoListContract.ITodoListPresenter {

    companion object {
        const val firstPage = 1
    }

    override fun refreshTodoList(type: Int, status: Int) {
        val disposable = mModel.loadTodoList(page = firstPage, type = type, status = status)
            .network2Main()
            .subscribeBy(
                onNext = {
                    val currentPage = it.curPage
                    val totalPage = it.pageCount
                    val list = it.datas
                    val hasMore = currentPage < totalPage
                    mView.showTodoList(list, currentPage, hasMore)
                },
                onError = {
                    Timber.e(it)
                    mView.refreshOrLoadFailed(true)
                }
            )
        addDisposable(disposable)
    }

    override fun loadTodoList(page: Int, type: Int, status: Int) {
        val disposable = mModel.loadTodoList(page = page, type = type, status = status)
            .network2Main()
            .subscribeBy(
                onNext = {
                    val currentPage = it.curPage
                    val totalPage = it.pageCount
                    val list = it.datas
                    val hasMore = currentPage < totalPage
                    mView.showTodoList(list, currentPage, hasMore)
                },
                onError = {
                    Timber.e(it)
                    mView.refreshOrLoadFailed(false)
                }
            )
        addDisposable(disposable)
    }

    override fun deleteTodo(index: Int, id: Int) {
        val disposable = mModel.deleteTodo(id)
            .network2Main()
            .subscribeBy(onError = {
                Timber.e(it)
                mView.showError(it.message ?: "删除失败")
            }, onNext = {
                if (it.first == 0) {
                    mView.remove(index)
                } else {
                    mView.showError("删除失败:${it.second}")
                }
            })
        addDisposable(disposable)
    }

    override fun updateTodoStatus(index: Int, id: Int, newStatus: Int) {
        val disposable = mModel.updateTodoStatus(id, newStatus)
            .network2Main()
            .subscribeBy(onError = {
                Timber.e(it)
                mView.showError(it.message ?: "更新失败")
            }, onNext = {
                if (it.first == 0) {
                    mView.remove(index)
                } else {
                    mView.showError("更新失败:${it.second}")
                }
            })
        addDisposable(disposable)
    }


}