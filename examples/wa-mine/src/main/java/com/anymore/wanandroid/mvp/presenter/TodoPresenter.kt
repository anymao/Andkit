package com.anymore.wanandroid.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.common.ext.network2Main
import com.anymore.wanandroid.mvp.contract.TodoContract
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by anymore on 2020/2/2.
 */
class TodoPresenter @Inject constructor(
    application: Application,
    mView: TodoContract.ITodoView,
    private val mModel: TodoContract.ITodoModel
) :
    BasePresenter<TodoContract.ITodoView>(application, mView),TodoContract.ITodoPresenter {
    override fun saveTodo(title: String, content: String, date: String, type: Int, priority: Int) {
        val disposable = mModel.saveTodo(title,content,date,type,priority)
            .network2Main()
            .subscribeBy(onError = {
                Timber.e(it)
                mView.showError(it.message?:"创建失败")
            },onNext = {
                if (it.first == 0){
                    mView.success(0,"创建成功")
                }else{
                    mView.showError("创建失败:${it.second}")
                }
            })
        addDisposable(disposable)
    }

    override fun updateTodo(
        id: Int,
        title: String,
        content: String,
        date: String,
        type: Int,
        priority: Int
    ) {
        val disposable = mModel.updateTodo(id,title,content,date,type,priority)
            .network2Main()
            .subscribeBy(onError = {
                Timber.e(it)
                mView.showError(it.message?:"更新失败")
            },onNext = {
                if (it.first == 0){
                    mView.success(1,"更新成功")
                }else{
                    mView.showError("更新失败:${it.second}")
                }
            })
        addDisposable(disposable)
    }

    override fun deleteTodo(id: Int) {
        val disposable = mModel.deleteTodo(id)
            .network2Main()
            .subscribeBy(onError = {
                Timber.e(it)
                mView.showError(it.message?:"删除失败")
            },onNext = {
                if (it.first == 0){
                    mView.success(2,"删除成功" +
                            "")
                }else{
                    mView.showError("删除失败:${it.second}")
                }
            })
        addDisposable(disposable)
    }
}