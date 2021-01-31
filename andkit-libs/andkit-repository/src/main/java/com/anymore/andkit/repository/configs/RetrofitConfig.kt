package com.anymore.andkit.repository.configs

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitConfig : BaseConfig<Retrofit.Builder> {

    companion object {
        /**
         * default impl
         */
        val DEFAULT = object : RetrofitConfig {
            override fun config(context: Context, builder: Retrofit.Builder) {
                builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
            }
        }
    }
}