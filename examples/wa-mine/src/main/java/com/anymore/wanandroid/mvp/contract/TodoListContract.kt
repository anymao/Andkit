package com.anymore.wanandroid.mvp.contract

import com.anymore.andkit.mvp.BaseContract
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.entry.TodoData
import io.reactivex.Observable

/**
 * Created by anymore on 2020/1/30.
 */
interface TodoListContract {
    interface ITodoListView : BaseContract.IBaseView {
        fun showTodoList(todos:List<Todo>,add:Boolean = false)
    }

    interface ITodoListPresenter : BaseContract.IBasePresenter {
        fun loadTodoList(page: Int,type: Int)
    }

    interface ITodoListModel : BaseContract.IBaseModel {
        fun loadTodoList(
            page: Int,
            status: Int? = null,
            type: Int? = null,
            priority: Int? = null,
            orderby: Int? = null
        ): Observable<TodoData>
    }
}