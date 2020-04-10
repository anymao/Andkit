package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpActivity
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.common.adapter.FragmentsAdapter
import com.anymore.wanandroid.common.entry.FragmentItem
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.contract.TodoTabContract
import com.anymore.wanandroid.mvp.view.fragment.TodoListFragment
import com.anymore.wanandroid.route.MINE_TODO
import com.anymore.wanandroid.route.MINE_TODO_LIST
import com.anymore.wanandroid.route.NEED_LOGIN_FLAG
import com.anymore.wanandroid.route.TODO_OPERATION
import kotlinx.android.synthetic.main.wm_activity_todo_tab.*
import org.jetbrains.anko.selector

/**
 * Created by anymore on 2020/1/29.
 */
@Route(path = MINE_TODO_LIST, extras = NEED_LOGIN_FLAG)
class TodoTabActivity : BaseMvpActivity<TodoTabContract.ITodoTabPresenter>(),
    TodoTabContract.ITodoTabView {

    companion object {
        private val todoTypes by lazy {
            listOf(
                ContextProvider.getApplicationContext().getString(R.string.wm_todo_only_use),
                ContextProvider.getApplicationContext().getString(R.string.wm_todo_study),
                ContextProvider.getApplicationContext().getString(R.string.wm_todo_work),
                ContextProvider.getApplicationContext().getString(R.string.wm_todo_life)
            )
        }
    }

    private val mOnTypeSelectedListeners: MutableList<OnTodoTypeSelectedListener> = mutableListOf()

    override fun initView(savedInstanceState: Bundle?) = R.layout.wm_activity_todo_tab

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(toolbar)
        title = ""
        tvTitle.setOnClickListener {
            selector(
                title = "切换Todo类型",
                items = todoTypes,
                onClick = { dialog, index ->
                    tvTitle.text = todoTypes[index]
                    for (listener in mOnTypeSelectedListeners) {
                        listener.onSelected(index, todoTypes[index])
                    }
                }
            )
        }
        fabAddTodo.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_TODO)
                .withInt(TODO_OPERATION, TodoActivity.TYPE_CREATE)
                .navigation(this)
        }
        val fragments = arrayListOf(
            FragmentItem(
                TodoListFragment.newInstance(TodoListFragment.STATUS_UNDO),
                getString(R.string.wm_type_undo)
            ),
            FragmentItem(
                TodoListFragment.newInstance(TodoListFragment.STATUS_DONE),
                getString(R.string.wm_type_done)
            )
        )
        val adapter = FragmentsAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
        tabBar.setupWithViewPager(viewPager)
    }

    override fun useFragment() = true

    fun addOnTodoTypeSelectedListener(listener: OnTodoTypeSelectedListener) {
        mOnTypeSelectedListeners.add(listener)
    }

    fun removeOnTodoTypeSelectedListener(listener: OnTodoTypeSelectedListener) {
        mOnTypeSelectedListeners.remove(listener)
    }

    interface OnTodoTypeSelectedListener {
        fun onSelected(type: Int, typeName: String)
    }
}