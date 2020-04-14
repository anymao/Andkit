package com.anymore.wanandroid.articles.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.anymore.wanandroid.articles.BR
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.entry.Article

class ArticlesPagingAdapter(context: Context) :
    NetStatusPagingAdapter<Article>(diffCallback) {

    var mItemEventHandler: OnItemEventHandler? = null

    override fun getItemViewLayout(position: Int) = R.layout.wa_item_article

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position < getDataItemCount()) {
            holder.mBinding.setVariable(BR.entry, getItem(position))
            mItemEventHandler?.let {
                holder.mBinding.setVariable(BR.eventHandler, it)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                (oldItem.id == newItem.id)
        }
    }

    interface OnItemEventHandler {
        fun onClick(item: Article)
        fun onCollectClick(item: Article)
    }
}

