package com.anymore.andkit.lifecycle.key

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.internal.Beta
import kotlin.reflect.KClass

/**
 * Created by liuyuanmao on 2019/2/22.
 */
@Beta
@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class ViewModelKey(val value: KClass<out ViewModel>)