package com.anymore.andkit.mvvm

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * 对于Kotlin有关Class反射等相关的方法扩展
 * Created by liuyuanmao on 2019/11/7.
 */
fun<T:Any> parseClassGenericParams(clazz: KClass<*>,index:Int = 0):Class<T>{
    val parameterizedType = clazz.java.genericSuperclass as ParameterizedType
    val genericParams = parameterizedType.actualTypeArguments
    require(!genericParams.isNullOrEmpty()) { "Class<${clazz.qualifiedName}> has no generic params!" }
    require(index in index until genericParams.size){ "Class<${clazz.qualifiedName}> has ${genericParams.size} but you call index = $index" }
    @Suppress("UNCHECKED_CAST")
    return genericParams[index] as Class<T>
}

/**
 * 获取自己类的Class的泛型参数列表中第[index]个泛型参数的类型
 */
fun<T:Any> Any.getClassGenericParams(index: Int = 0):Class<T>{
    return parseClassGenericParams(this::class,index)
}