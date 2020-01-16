package com.anymore.andkit.repository

import android.app.Application
import android.util.SparseArray
import androidx.collection.LruCache
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anymore.cachekit.DataCache
import com.anymore.andkit.repository.di.module.RepositoryConfigsModule
import dagger.Lazy
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liuyuanmao on 2019/2/28.
 */

interface IRepositoryManager {
    fun <T> obtainRetrofitService(key: Int, retrofitClass: Class<T>): T
    /**
     * 获取磁盘数据库对象，构建的磁盘数据库会被缓存，以便下一次获取
     * @param databaseClass 要获取的RoomDatabase类型
     * @param databaseName 数据库名称
     */
    fun <DB : RoomDatabase> obtainRoomDatabase(databaseClass: Class<DB>, databaseName: String): DB

    /**
     * 获取一个内存数据库对象,内存数据库对象存在于应用程序运行期间，不会被持久化，如果在应用程序
     * 运行期间想要复用这个数据库，应当对其进行缓存
     * @param databaseClass 要获取的RoomDatabase类型
     * @param tag 相当于一个key，在应用程序存在多个相同实例的时候，根据tag来获取想要的被缓存的实例
     */
    fun <DB : RoomDatabase> obtainMemoryRoomDatabase(
        databaseClass: Class<DB>,
        tag: String = "default"
    ): DB

    /**
     * 获取一个[DataCache]对象，这个是被Application所持有的，可用于全局的双缓存场景
     */
    fun obtainDataCache(): DataCache
}

@Singleton
class RepositoryManager @Inject constructor(
    private val mApplication: Application,
    private val mRetrofits: Lazy<SparseArray<Retrofit>>,
    private val mRoomDatabaseConfig: RepositoryConfigsModule.RoomDatabaseConfig,
    private val mDataCache: Lazy<DataCache>
) : IRepositoryManager {

    private val mRetrofitCache by lazy { LruCache<String, Any>(500) }
    private val mRoomDatabaseCache by lazy { LruCache<String, RoomDatabase>(500) }
    private val mMemoryRoomDatabaseCache by lazy {
        LruCache<String, RoomDatabase>(
            500
        )
    }

    override fun <T> obtainRetrofitService(key: Int, retrofitClass: Class<T>): T {
        var result = mRetrofitCache.get(retrofitClass.name)
        if (result == null) {
            result = mRetrofits.get().get(key).create(retrofitClass)
            mRetrofitCache.put(retrofitClass.name, result!!)
        }
        @Suppress("UNCHECKED_CAST")
        return result as T
    }

    override fun <DB : RoomDatabase> obtainRoomDatabase(
        databaseClass: Class<DB>,
        databaseName: String
    ): DB {
        var result = mRoomDatabaseCache.get(databaseClass.name)
        if (result == null) {
            val builder = Room.databaseBuilder(mApplication, databaseClass, databaseName)
            mRoomDatabaseConfig.config(mApplication, builder)
            result = builder.build()
            mRoomDatabaseCache.put(databaseClass.name, result)
        }
        @Suppress("UNCHECKED_CAST")
        return result as DB
    }

    override fun <DB : RoomDatabase> obtainMemoryRoomDatabase(
        databaseClass: Class<DB>,
        tag: String
    ): DB {
        val key = "${databaseClass.name}_$tag"
        var result = mMemoryRoomDatabaseCache.get(key)
        if (result == null) {
            val builder = Room.inMemoryDatabaseBuilder(mApplication, databaseClass)
            result = builder.build()
            mMemoryRoomDatabaseCache.put(key, result)
        }
        @Suppress("UNCHECKED_CAST")
        return result as DB
    }

    override fun obtainDataCache(): DataCache = mDataCache.get()
}