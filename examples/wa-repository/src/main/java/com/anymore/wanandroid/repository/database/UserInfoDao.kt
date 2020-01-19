package com.anymore.wanandroid.repository.database

import androidx.room.*
import com.anymore.wanandroid.repository.database.entry.UserInfo
import io.reactivex.Maybe

/**
 * Created by anymore on 2019/4/20.
 */

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM UserInfo WHERE online LIMIT 1")
    fun getCurrentUser():Maybe<UserInfo>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userInfo: UserInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfo: UserInfo)

    @Delete
    fun delete(userInfo: UserInfo)

    @Query("UPDATE UserInfo SET online=:newStatus WHERE online")
    fun updateOnlineStatus(newStatus:Boolean)
}