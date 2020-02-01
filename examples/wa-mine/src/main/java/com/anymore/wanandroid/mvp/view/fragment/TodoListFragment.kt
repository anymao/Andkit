package com.anymore.wanandroid.mvp.view.fragment

import android.os.Bundle
import com.anymore.andkit.mvp.BaseMvpFragment
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.contract.TodoListContract

/**
 * Created by anymore on 2020/1/30.
 */
class TodoListFragment : BaseMvpFragment<TodoListContract.ITodoListPresenter>(),
    TodoListContract.ITodoListView {

    companion object {
        const val TYPE_UNDO = 0
        const val TYPE_DONE = 1
        const val EXTRA_TYPE = "TodoListFragment#TYPE"
        fun newInstance(type: Int): TodoListFragment {
            val bundle = Bundle()
            bundle.putInt(EXTRA_TYPE, type)
            return newInstance(bundle)
        }

        fun newInstance(bundle: Bundle): TodoListFragment {
            val fragment = TodoListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutRes() = R.layout.wm_fragment_todo_list

    override fun showTodoList(todos: List<Todo>, add: Boolean) {

    }
}