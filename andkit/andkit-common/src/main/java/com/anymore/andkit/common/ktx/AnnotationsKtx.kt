package com.anymore.andkit.common.ktx

import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Created by anymore on 2022/4/1.
 */
fun <T : Annotation> Any.annotationOrNull(clazz: KClass<T>): T? {
    return this::class.annotations.firstOrNull { clazz.isInstance(it) }?.let { clazz.cast(it) }
}

inline fun <reified T : Annotation> Any.annotationOrNull(): T? = annotationOrNull(T::class)

fun <T : Annotation> Any.requireAnnotation(
    clazz: KClass<T>,
    lazyMessage: () -> Any = { "class ${this::class.qualifiedName} must annotation by ${clazz.qualifiedName}" }
) = requireNotNull(annotationOrNull(clazz), lazyMessage)

inline fun <reified T : Annotation> Any.requireAnnotation(
    noinline lazyMessage: () -> Any = { "class ${this::class.qualifiedName} must annotation by ${T::class.qualifiedName}" }
): T = requireAnnotation(T::class, lazyMessage)


fun <T : Annotation> Any.annotations(clazz: KClass<T>): List<T> {
    return this::class.annotations.filter { clazz.isInstance(it) }.map { clazz.cast(it) }
}

inline fun <reified T : Annotation> Any.annotations() = annotations(T::class)