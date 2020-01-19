package com.anymore.wanandroid.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anymore.wanandroid.repository.database.entry.UserInfo

/**
 * Created by anymore on 2019/4/20.
 */
@Database(entities = [UserInfo::class],version = 1)
abstract class AppDatabase: RoomDatabase(){
    companion object{
        const val DB_NAME = "WanAndroid.db"
    }
    abstract fun userInfoDao(): UserInfoDao

}