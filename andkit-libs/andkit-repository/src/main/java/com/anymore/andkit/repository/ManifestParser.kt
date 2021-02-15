package com.anymore.andkit.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.anymore.andkit.repository.configs.RepositoryConfig

/**
 * Created by liuyuanmao on 2019/3/11.
 */
@Deprecated("use @RepositoryConfiguration")
internal object ManifestParser {
    const val REPOSITORY_CONFIG = "repositoryConfig"

    /**
     * 从Manifest 的mate-data中读取配置
     */
    fun parseRepositoryConfig(context: Context): List<RepositoryConfig> {
        return parse(context, REPOSITORY_CONFIG)
    }

    private inline fun <reified T> parse(
        context: Context,
        key: String
    ): List<T> {
        val result = ArrayList<T>()
        val applicationInfo: ApplicationInfo? = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        if (applicationInfo != null && applicationInfo.metaData?.keySet()?.isNotEmpty() == true) {
            for (dataKey in applicationInfo.metaData.keySet()) {
                if (TextUtils.equals(key, applicationInfo.metaData[dataKey] as String)) {
                    result.add(createConfigByName(dataKey))
                }
            }
        }
        return result
    }

    private inline fun <reified T> createConfigByName(className: String): T {
        val clazz: Class<*>
        try {
            clazz = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find ConfigRepository implementation", e)
        }

        val config: Any
        try {
            config = clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(
                "Unable to instantiate ConfigRepository implementation for $clazz",
                e
            )
        } catch (e: IllegalAccessException) {
            throw RuntimeException(
                "Unable to instantiate ConfigRepository implementation for $clazz",
                e
            )
        }

        if (config !is T) {
            throw RuntimeException("Expected instance of ConfigRepository, but found: $config")
        }
        return config
    }
}