package com.anymore.andkit.common.ktx

import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Created by anymore on 2022/4/1.
 */
fun <A : Annotation> Any.annotationsOf(clazz: KClass<A>): List<A> =
    this::class.annotations.filter { clazz.isInstance(it) }.map { clazz.cast(it) }

inline fun <reified A : Annotation> Any.annotationsOf(): List<A> = annotationsOf(A::class)

fun <A : Annotation> Any.annotationOf(clazz: KClass<A>): A? =
    this::class.annotations.firstOrNull { clazz.isInstance(it) }?.let { clazz.cast(it) }

inline fun <reified A : Annotation> Any.annotationOf() = annotationOf(A::class)

fun <A : Annotation> Any.requireAnnotation(
    clazz: KClass<A>,
    lazyMessage: (() -> Any) = { "the class ${this.className} must annotation with ${clazz.qualifiedName}" }
): A =
    requireNotNull(annotationOf(clazz), lazyMessage)

inline fun <reified A : Annotation> Any.requireAnnotation(
    noinline lazyMessage: (() -> Any) = { "the class ${this.className} must annotation with ${A::class.qualifiedName}" }
): A = requireAnnotation(A::class, lazyMessage)