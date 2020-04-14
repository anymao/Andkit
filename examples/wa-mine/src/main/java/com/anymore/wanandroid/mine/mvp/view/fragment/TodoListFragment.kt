package com.anymore.wanandroid.mine.mvp.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpFragment
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.mine.entry.Todo
import com.anymore.wanandroid.mine.event.TodoChangedEvent
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mine.mvp.contract.TodoListContract
import com.anymore.wanandroid.mine.mvp.view.activity.TodoTabActivity
import com.anymore.wanandroid.mine.mvp.view.adapter.TodoListAdapter
import com.anymore.wanandroid.route.MINE_TODO
import com.anymore.wanandroid.route.TODO_DATA
import com.anymore.wanandroid.route.TODO_OPERATION
import kotlinx.android.synthetic.main.wm_fragment_todo_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by anymore on 2020/1/30.
 */

class TodoListFragment : BaseMvpFragment<TodoListContract.ITodoListPresenter>(),
    TodoListContract.ITodoListView, TodoTabActivity.OnTodoTypeSelectedListener {


    private var mTodoType: Int = DEFAULT_TODO_TYPE
    private var mTodoTypeName: String = DEFAULT_TODO_TYPE_NAME
    private var currentPage = 1
    private var mTodoStatus: Int = STATUS_UNDO

    private val adapter by lazy {
        TodoListAdapter(requireContext()).also {
            it.setOnClickListener { _, todo ->
                ARouter.getInstance()
                    .build(MINE_TODO)
                    .withInt(TODO_OPERATION, 1)
                    .withSerializable(TODO_DATA, todo)
                    .navigation(requireContext())
            }
            it.setOnDeleteClickListener { i, todo -> mPresenter.deleteTodo(i, todo.id) }
            it.setOnCompleteClickListener { i, todo ->
                val newStatus = (todo.status + 1) % 2
                mPresenter.updateTodoStatus(i, todo.id, newStatus)
            }
        }
    }


    companion object {
        const val firstPage = 1
        const val DEFAULT_TODO_TYPE = 0
        val DEFAULT_TODO_TYPE_NAME: String =
            ContextProvider.getApplicationContext().getString(R.string.wm_todo_only_use)

        const val STATUS_UNDO = 0
        const val STATUS_DONE = 1
        const val EXTRA_STATUS = "TodoListFragment#STATUS"
        fun newInstance(type: Int): TodoListFragment {
            val bundle = Bundle()
            bundle.putInt(EXTRA_STATUS, type)
            return newInstance(bundle)
        }

        fun newInstance(bundle: Bundle): TodoListFragment {
            val fragment = TodoListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutRes() = R.layout.wm_fragment_todo_list

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mTodoStatus = arguments?.getInt(EXTRA_STATUS, STATUS_UNDO) ?: STATUS_UNDO
        val containerActivity = requireActivity()
        if (containerActivity is TodoTabActivity) {
            containerActivity.addOnTodoTypeSelectedListener(this)
        }
        rvList.adapter = adapter
        rvList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        srl.setOnRefreshListener {
            refreshTodoList()
        }
        srl.setOnLoadMoreListener {
            mPresenter.loadTodoList(currentPage + 1, mTodoType, mTodoStatus)
        }
        refreshTodoList()
    }

    override fun showTodoList(todos: List<Todo>, pageNumber: Int, hasMore: Boolean) {
        if (pageNumber == firstPage) {
            adapter.setData(todos)
            srl.finishRefresh()
//            if (todos.isNullOrEmpty()){
//                toast("这里☞空空的~")
//            }
        } else {
            adapter.addData(todos)
            srl.finishLoadMore()
        }
        currentPage = pageNumber
        srl.setNoMoreData(!hasMore)
    }

    override fun refreshOrLoadFailed(refresh: Boolean) {
        if (refresh) {
            srl.finishRefresh(false)
        } else {
            srl.finishLoadMore(false)
        }
        showError("加载失败!")
    }

    override fun remove(index: Int) {
        adapter.remove(index)
        EventBus.getDefault().post(TodoChangedEvent("remove"))
    }

    override fun onSelected(type: Int, typeName: String) {
        if (mTodoType != type) {
            mTodoType = type
            mTodoTypeName = typeName
            refreshTodoList()
        }
    }

    override fun onDestroyView() {
        val containerActivity = requireActivity()
        if (containerActivity is TodoTabActivity) {
            containerActivity.removeOnTodoTypeSelectedListener(this)
        }
        super.onDestroyView()
    }

    private fun refreshTodoList() {
        mPresenter.refreshTodoList(mTodoType, mTodoStatus)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTodoListChanged(event: TodoChangedEvent) {
        refreshTodoList()
    }
}