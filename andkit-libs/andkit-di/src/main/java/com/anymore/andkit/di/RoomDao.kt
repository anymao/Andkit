package com.anymore.andkit.di

import kotlin.reflect.KClass

/**
 * 用于注解Room的Dao接口
 * Created by anymore on 2021/1/31.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class RoomDao(val database: KClass<*>)
