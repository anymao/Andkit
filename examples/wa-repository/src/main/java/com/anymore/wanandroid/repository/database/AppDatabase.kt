package com.anymore.wanandroid.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anymore.wanandroid.repository.database.entry.TensorFlowConfig
import com.anymore.wanandroid.repository.database.entry.UserInfo

/**
 * Created by anymore on 2019/4/20.
 */
@Database(entities = [UserInfo::class, TensorFlowConfig::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "WanAndroid.db"
    }

    abstract fun userInfoDao(): UserInfoDao

    abstract fun tensorFlowConfigDao(): TensorFlowConfigDao

}