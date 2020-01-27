package com.anymore.wanandroid.adapter

import android.content.Context
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.entry.Knowledge

/**
 * Created by liuyuanmao on 2019/4/28.
 */
class KnowledgesAdapter(mContext: Context, mEventHandler: OnItemEventHandler? = null) :
    BindingRecyclerViewAdapter<Knowledge>(mContext, R.layout.wa_item_knowledge) {

    init {
        mItemEventHandler = mEventHandler
    }

    interface OnItemEventHandler {
        fun onClick(item: Knowledge)
    }
}