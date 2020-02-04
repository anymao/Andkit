package com.anymore.wanandroid.mvp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.mine.R
import kotlinx.android.synthetic.main.wm_item_todo.view.*

/**
 * Created by anymore on 2020/2/2.
 */
class TodoListAdapter(private val context: Context) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {


    private val data: MutableList<Todo> = mutableListOf()

    private var mOnClickListener: ((Int, Todo) -> Unit)? = null
    private var mOnCompleteClickLisener: ((Int, Todo) -> Unit)? = null
    private var mOnDeleteClickListener: ((Int, Todo) -> Unit)? = null


    fun setOnClickListener(listener: (Int, Todo) -> Unit) {
        mOnClickListener = listener
    }

    fun setOnCompleteClickListener(listener: (Int, Todo) -> Unit) {
        mOnCompleteClickLisener = listener
    }

    fun setOnDeleteClickListener(listener: (Int, Todo) -> Unit) {
        mOnDeleteClickListener = listener
    }


    fun setData(newData: List<Todo>) {
        data.clear()
        addData(newData)
    }

    fun addData(newData: List<Todo>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        if (index >= 0 && index < data.size) {
            data.removeAt(index)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wm_item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = data[position]
        holder.itemView.tvTodoName.text = todo.title
        holder.itemView.tvTodoDate.text = todo.completeDateStr
        holder.itemView.tvComplete.text = if (todo.status == 0) {
            "完成"
        } else {
            "未完成"
        }
        holder.itemView.llTodo.setOnClickListener {
            mOnClickListener?.invoke(position, todo)
        }
        holder.itemView.tvComplete.setOnClickListener {
            mOnCompleteClickLisener?.invoke(position, todo)
        }
        holder.itemView.tvDelete.setOnClickListener {
            mOnDeleteClickListener?.invoke(position, todo)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}