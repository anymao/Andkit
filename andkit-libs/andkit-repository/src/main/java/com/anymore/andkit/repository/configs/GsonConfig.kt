package com.anymore.andkit.repository.configs

import android.content.Context
import com.google.gson.GsonBuilder

interface GsonConfig : BaseConfig<GsonBuilder> {

    companion object {
        /**
         * default impl
         */
        val DEFAULT = object : GsonConfig {
            override fun config(context: Context, builder: GsonBuilder) {
                //todo
            }
        }
    }
}
