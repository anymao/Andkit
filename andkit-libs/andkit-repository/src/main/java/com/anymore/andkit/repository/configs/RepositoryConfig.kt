package com.anymore.andkit.repository.configs

/**
 * Created by anymore on 2021/1/30.
 */
interface RepositoryConfig {
    fun dataCacheConfig(): DataCacheConfig = DataCacheConfig.DEFAULT
    fun gsonConfig(): GsonConfig = GsonConfig.DEFAULT
    fun okHttpConfig(): OkHttpConfig = OkHttpConfig.DEFAULT
    fun retrofitConfig(): RetrofitConfig = RetrofitConfig.DEFAULT
    fun roomDatabaseConfig(): RoomDatabaseConfig = RoomDatabaseConfig.DEFAULT

    companion object {
        val DEFAULT = object : RepositoryConfig {}
    }
}