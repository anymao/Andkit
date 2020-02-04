package com.anymore.wanandroid.mvp.contract

import com.anymore.andkit.mvp.BaseContract
import com.anymore.wanandroid.entry.Todo

/**
 * Created by anymore on 2020/1/30.
 */
interface TodoListContract {
    interface ITodoListView : BaseContract.IBaseView {
        fun showTodoList(todos:List<Todo>,pageNumber:Int,hasMore:Boolean = true)
        fun remove(index:Int)
    }

    interface ITodoListPresenter : BaseContract.IBasePresenter {
        fun refreshTodoList(type: Int,status: Int)
        fun loadTodoList(page: Int,type: Int,status: Int)
        fun deleteTodo(index: Int,id: Int)
        fun updateTodoStatus(index: Int,id: Int,newStatus:Int)
    }

}