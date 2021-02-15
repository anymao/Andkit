package com.anymore.andkit.di

import kotlin.reflect.KClass

/**
 * Created by anymore on 2021/2/14.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class DataBundle(val clazz: KClass<*>)
