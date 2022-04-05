package com.anymore.andkit.rpc.converter

import androidx.annotation.IntRange
import com.anymore.andkit.rpc.ErrorResponseException
import com.anymore.andkit.rpc.ResponseWrapper
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Created by anymore on 2022/3/30.
 */
class HuskResponseConverter<T>(
    private val gson: Gson,
    private val type: Type,
    private val wrapper: KClass<out ResponseWrapper<*>>,
    private val huskCodes: LongArray
) :
    Converter<ResponseBody, T> {

    companion object {
        fun factory(gson: Gson): Converter.Factory {
            return Factory(gson)
        }
    }

    override fun convert(value: ResponseBody): T? {
        val json = value.string()
        val response = gson.fromJson<ResponseWrapper<T>>(json, HuskResponseType(type, wrapper))
            ?: throw ErrorResponseException(code = null, message = "Response is null")
        response.check(*huskCodes)
        return response.data
    }


    private class HuskResponseType(private val type: Type, private val wrapper: KClass<*>) :
        ParameterizedType {
        override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
        override fun getRawType(): Type = wrapper.java
        override fun getOwnerType(): Type? = null
    }


    private class Factory(private val gson: Gson) : Converter.Factory() {

        private val converter = GsonConverterFactory.create(gson)

        override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<out Annotation>,
            methodAnnotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = converter.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)

        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            val huskWith = annotations.firstOrNull { it is HuskWith } as? HuskWith
            val huskWrapperClass = huskWith?.value
            val huskCodes = huskWith?.codes
            return if (huskWrapperClass == null) {
                converter.responseBodyConverter(type, annotations, retrofit)
            } else {
                HuskResponseConverter<Any>(
                    gson,
                    type,
                    huskWrapperClass,
                    huskCodes ?: longArrayOf(0L)
                )
            }
        }
    }
}