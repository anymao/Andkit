package com.anymore.andkit.rpc.converter

import com.anymore.andkit.rpc.ResponseWrapper
import kotlin.reflect.KClass

/**
 * Created by anymore on 2022/3/30.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HuskWith(val value: KClass<out ResponseWrapper<*>>, val codes: LongArray = [0L])
