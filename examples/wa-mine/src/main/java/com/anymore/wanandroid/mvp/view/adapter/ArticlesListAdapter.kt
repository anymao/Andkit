package com.anymore.wanandroid.mvp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anymore.wanandroid.entry.Article1
import com.anymore.wanandroid.mine.R
import kotlinx.android.synthetic.main.wm_item_article.view.*

/**
 * Created by anymore on 2020/2/2.
 */
class ArticlesListAdapter(private val context: Context) :
    RecyclerView.Adapter<ArticlesListAdapter.ViewHolder>() {


    private val data: MutableList<Article1> = mutableListOf()

    private var mOnClickListener: ((Int, Article1) -> Unit)? = null
    private var mOnUncollectedListener:((Int,Article1)->Unit)?=null


    fun setOnClickListener(listener: (Int, Article1) -> Unit) {
        mOnClickListener = listener
    }

    fun setOnUncollectedListener(listener: (Int, Article1) -> Unit){
        mOnUncollectedListener = listener
    }


    fun setData(newData: List<Article1>) {
        data.clear()
        addData(newData)
    }

    fun addData(newData: List<Article1>) {
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
        val view = LayoutInflater.from(context).inflate(R.layout.wm_item_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data[position]
        holder.itemView.tvArticleName.text = article.title
        holder.itemView.tvArticleAuthor.text = article.author
        holder.itemView.tvArticleTime.text = article.niceDate
        holder.itemView.clArticlesRoot.setOnClickListener {
            mOnClickListener?.invoke(position,article)
        }
        holder.itemView.tvDelete.setOnClickListener {
            mOnUncollectedListener?.invoke(position,article)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}