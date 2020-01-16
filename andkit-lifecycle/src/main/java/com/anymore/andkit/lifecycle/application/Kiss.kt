package com.anymore.andkit.lifecycle.application

import kotlin.reflect.KClass

/**
 * Created by liuyuanmao on 2019/11/4.
 */
@Repeatable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Kiss(val value: KClass<out AbsApplicationWrapper>, val priority: Int = 0)
