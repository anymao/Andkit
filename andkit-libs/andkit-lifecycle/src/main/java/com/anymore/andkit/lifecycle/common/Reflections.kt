package com.anymore.andkit.lifecycle.common

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * Created by anymore on 2021/1/30.
 */
fun <T : Any> parseClassGenericParams(clazz: KClass<*>, index: Int = 0): Class<T> {
    val parameterizedType = clazz.java.genericSuperclass as ParameterizedType
    val genericParams = parameterizedType.actualTypeArguments
    require(!genericParams.isNullOrEmpty()) { "Class<${clazz.qualifiedName}> has no generic params!" }
    require(index in index until genericParams.size) { "Class<${clazz.qualifiedName}> has ${genericParams.size} but you call index = $index" }
    @Suppress("UNCHECKED_CAST")
    return genericParams[index] as Class<T>
}

/**
 * 获取自己类的Class的泛型参数列表中第[index]个泛型参数的类型
 */
fun <T : Any> Any.getClassGenericParams(index: Int = 0): Class<T> {
    return parseClassGenericParams(this::class, index)
}

fun <T : Any, A : Annotation> T?.annotation(annotationClass: KClass<A>): A? =
    this?.javaClass?.getAnnotation(annotationClass.java)

fun <T : Any, A : Annotation> T?.annotationOrThrow(annotationClass: KClass<A>): A =
    annotation(annotationClass)
        ?: throw RuntimeException("Miss $annotationClass in ${this?.classTag}")

fun KClass<*>.isSubClassOf(superClass: KClass<*>) = superClass.java.isAssignableFrom(this.java)