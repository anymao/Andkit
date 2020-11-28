package com.anymore.wanandroid.repository.database.entry

import androidx.room.PrimaryKey
import com.anymore.wanandroid.common.ext.now

abstract class BaseModel {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var createTime: Long = now
    var updateTime: Long = now
}