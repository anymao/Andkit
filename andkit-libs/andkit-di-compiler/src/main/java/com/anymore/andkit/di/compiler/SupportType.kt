package com.anymore.andkit.di.compiler

import com.anymore.andkit.di.DataBundle
import com.anymore.andkit.di.RepositoryConfiguration
import com.anymore.andkit.di.RetrofitService
import com.anymore.andkit.di.RoomDao
import kotlin.reflect.KClass

/**
 * Created by anymore on 2021/2/14.
 */
internal enum class SupportType(val clazz: KClass<out Annotation>) {
    RetrofitServiceType(RetrofitService::class),
    RoomDaoType(RoomDao::class),
    BundleType(DataBundle::class),
    RepositoryConfigurationType(RepositoryConfiguration::class);

    companion object {
        fun getSupportTypes(): MutableSet<String> {
            return values().mapNotNull { it.clazz.java.name }.toMutableSet()
        }
    }
}