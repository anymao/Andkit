package com.anymore.wanandroid.repository.database

import androidx.room.Dao
import androidx.room.Query
import com.anymore.andkit.di.RoomDao
import com.anymore.wanandroid.repository.database.entry.UserInfo
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by anymore on 2019/4/20.
 */

@RoomDao(database = AppDatabase::class)
@Dao
abstract class UserInfoDao : BaseDao<UserInfo>() {
    @Query("SELECT * FROM UserInfo WHERE online LIMIT 1")
    abstract fun getCurrentUser(): Maybe<UserInfo>

    @Query("UPDATE UserInfo SET online=:newStatus WHERE online")
    abstract fun updateOnlineStatus(newStatus: Boolean)

    @Query("DELETE FROM UserInfo")
    abstract fun deleteAll()

    @Query("SELECT * FROM UserInfo")
    abstract fun loadAllUser(): Flowable<List<UserInfo>>
}