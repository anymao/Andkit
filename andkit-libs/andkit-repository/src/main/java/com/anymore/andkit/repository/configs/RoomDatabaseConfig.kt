package com.anymore.andkit.repository.configs

import android.content.Context
import androidx.room.RoomDatabase

interface RoomDatabaseConfig : BaseConfig<RoomDatabase.Builder<*>> {

    companion object {
        /**
         * default impl
         */
        val DEFAULT = object : RoomDatabaseConfig {
            override fun config(context: Context, builder: RoomDatabase.Builder<*>) {

            }
        }
    }
}