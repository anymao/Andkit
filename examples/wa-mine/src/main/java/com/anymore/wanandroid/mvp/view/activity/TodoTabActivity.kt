package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import android.util.SparseArray
import android.view.Menu
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.mvp.BaseMvpActivity
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.common.adapter.FragmentsAdapter
import com.anymore.wanandroid.common.entry.FragmentItem
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.contract.TodoTabContract
import com.anymore.wanandroid.mvp.view.fragment.TodoListFragment
import com.anymore.wanandroid.route.MINE_TODO
import kotlinx.android.synthetic.main.wm_activity_todo_tab.*

/**
 * Created by anymore on 2020/1/29.
 */
@Route(path = MINE_TODO)
class TodoTabActivity : BaseMvpActivity<TodoTabContract.ITodoTabPresenter>(),
    TodoTabContract.ITodoTabView {
    companion object{
        private val todoTypeMap:SparseArray<String> = SparseArray()
        init {
            todoTypeMap.put(0,ContextProvider.getApplicationContext().getString(R.string.wm_todo_only_use))
            todoTypeMap.put(1,ContextProvider.getApplicationContext().getString(R.string.wm_todo_study))
            todoTypeMap.put(2,ContextProvider.getApplicationContext().getString(R.string.wm_todo_work))
            todoTypeMap.put(3,ContextProvider.getApplicationContext().getString(R.string.wm_todo_life))
        }
    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.wm_activity_todo_tab

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(toolbar)
        val fragments = arrayListOf(
            FragmentItem(
                TodoListFragment.newInstance(TodoListFragment.TYPE_UNDO),
                getString(R.string.wm_type_undo)
            ),
            FragmentItem(
                TodoListFragment.newInstance(TodoListFragment.TYPE_DONE),
                getString(R.string.wm_type_done)
            )
        )
        val adapter = FragmentsAdapter(supportFragmentManager,fragments)
        viewPager.adapter = adapter
        tabBar.setupWithViewPager(viewPager)
    }

    override fun useFragment()=true

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.wm_todo_type_menu,menu)
        return true
    }



    interface OnTodoTypeSelectedListener{
        fun onSelected(type:Int,typeName:String)
    }
}