package com.anymore.wanandroid.mvp.contract

import com.anymore.andkit.mvp.BaseContract
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.repository.base.PagedData
import io.reactivex.Observable

/**
 * Created by anymore on 2020/2/2.
 */
interface TodoContract {
    interface ITodoView : BaseContract.IBaseView {
        fun success(int: Int, message: String)
    }

    interface ITodoPresenter : BaseContract.IBasePresenter {
        fun saveTodo(title: String, content: String, date: String, type: Int, priority: Int = 1)
        fun updateTodo(
            id: Int,
            title: String,
            content: String,
            date: String,
            type: Int,
            priority: Int = 1
        )

        fun deleteTodo(id: Int)
    }

    interface ITodoModel : BaseContract.IBaseModel {
        fun saveTodo(
            title: String,
            content: String,
            date: String,
            type: Int,
            priority: Int = 1
        ): Observable<Pair<Int, String>>

        fun updateTodo(
            id: Int,
            title: String,
            content: String,
            date: String,
            type: Int,
            priority: Int = 1
        ): Observable<Pair<Int, String>>

        fun deleteTodo(id: Int): Observable<Pair<Int, String>>
        fun loadTodoList(
            page: Int,
            status: Int? = null,
            type: Int? = null,
            priority: Int? = null,
            orderby: Int? = null
        ): Observable<PagedData<Todo>>

        fun updateTodoStatus(id: Int,newStatus:Int): Observable<Pair<Int, String>>
    }
}