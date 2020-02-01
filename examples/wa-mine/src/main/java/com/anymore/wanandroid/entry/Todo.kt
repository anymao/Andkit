package com.anymore.wanandroid.entry

import java.io.Serializable

data class Todo(
    var completeDate: Any,
    var completeDateStr: String,
    var content: String,
    var date: Long,
    var dateStr: String,
    var id: Int,
    var priority: Int,
    var status: Int,
    var title: String,
    var type: Int,
    var userId: Int
):Serializable