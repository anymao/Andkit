package com.anymore.wanandroid.entry

import java.io.Serializable

class Todo :Serializable{
    var completeDate: Long = 0L
    var completeDateStr: String = ""
    var content: String = ""
    var date: Long = 0L
    var dateStr: String = ""
    var id: Int = 0
    var priority: Int = 0
    var status: Int = 0
    var title: String = ""
    var type: Int = 0
    var userId: Int = 0
}