package com.anymore.andkit.repository.configs

import android.content.Context
import com.anymore.cachekit.DataCache
import java.io.File

interface DataCacheConfig : BaseConfig<DataCache.Builder> {

    companion object {
        /**
         * default impl
         */
        val DEFAULT = object : DataCacheConfig {
            override fun config(context: Context, builder: DataCache.Builder) {
                val cacheDir = "${context.cacheDir}${File.separator}cachekit"
                val diskCacheSize = 64 * 1024 * 1024L
                builder.setMemoryCacheSize(128)
//                        .setDiskCacheVersion(context.applicationVersion)
                    .setDiskCacheDir(cacheDir)
                    .setDiskCacheSize(diskCacheSize)
            }

        }
    }
}