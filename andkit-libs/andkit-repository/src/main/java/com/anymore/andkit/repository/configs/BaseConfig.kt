package com.anymore.andkit.repository.configs

import android.content.Context

/**
 * Created by anymore on 2021/1/30.
 */
interface BaseConfig<T> {
    fun config(context: Context, builder: T)
}