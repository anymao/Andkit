package com.anymore.andkit.annotations

import com.anymore.andkit.lifecycle.application.AbsApplicationWrapper
import kotlin.reflect.KClass

/**
 * Created by liuyuanmao on 2019/11/4.
 */
@Repeatable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Kiss(val value: KClass<out AbsApplicationWrapper>, val priority: Int = 0)
