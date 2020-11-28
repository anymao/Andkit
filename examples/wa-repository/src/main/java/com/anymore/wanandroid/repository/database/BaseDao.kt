package com.anymore.wanandroid.repository.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.anymore.wanandroid.common.ext.now
import com.anymore.wanandroid.repository.database.entry.BaseModel

/**
 * Created by anymore on 2020/11/22.
 */
abstract class BaseDao<T : BaseModel> {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun batchInsert(ts: List<T>)


    @Delete
    abstract fun delete(t: T)

    @Delete
    abstract fun batchDelete(ts: List<T>)

    fun updateWithTime(t: T, updateTime: Long = now) {
        t.updateTime = updateTime
        update(t)
    }

}