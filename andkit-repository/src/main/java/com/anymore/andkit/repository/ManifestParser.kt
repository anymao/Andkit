package com.anymore.andkit.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.anymore.andkit.repository.di.module.RepositoryConfigsModule

/**
 * Created by liuyuanmao on 2019/3/11.
 */
internal object ManifestParser {
    const val REPOSITORY_MODULE_CONFIG = "repositoryModuleConfig"
    /**
     * 从Manifest 的mate-data中读取配置
     */
    fun parseRepositoryConfig(context: Context):List<RepositoryConfigsModule.RepositoryConfig>{
        return parse(context, REPOSITORY_MODULE_CONFIG)
    }

    private fun parse(context: Context,key:String):List<RepositoryConfigsModule.RepositoryConfig>{
        val result = ArrayList<RepositoryConfigsModule.RepositoryConfig>()
        val applicationInfo: ApplicationInfo? = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        if (applicationInfo != null && applicationInfo.metaData.keySet().isNotEmpty()){
            for (dataKey in applicationInfo.metaData.keySet()){
                if (TextUtils.equals(key,applicationInfo.metaData[dataKey] as String)){
                    result.add(createConfigByName(dataKey))
                }
            }
        }
        return result
    }

    private fun createConfigByName(className:String):RepositoryConfigsModule.RepositoryConfig{
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
            throw RuntimeException("Unable to instantiate ConfigRepository implementation for $clazz", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to instantiate ConfigRepository implementation for $clazz", e)
        }

        if (config !is RepositoryConfigsModule.RepositoryConfig) {
            throw RuntimeException("Expected instance of ConfigRepository, but found: $config")
        }
        return config
    }
}