package com.anymore.andkit.lifecycle.application

import android.app.Application
import android.content.res.Configuration
import timber.log.Timber

/**
 * Created by liuyuanmao on 2019/11/4.
 */
internal object ApplicationWrapperMonitor : IApplicationLifecycle {

    /**
     * 装饰类[PriorityApplicationWrapper]将注解上的信息封装，便于将应用程序中通过注解读取到的所有的
     * [AbsApplicationWrapper]进行排序，保证在相关生命周期执行方法的顺序性
     */
    private class PriorityApplicationWrapper(
        private val realWrapper: IApplicationLifecycle,
        val priority: Int
    ) : IApplicationLifecycle by realWrapper, Comparable<IApplicationLifecycle> {

        override fun compareTo(other: IApplicationLifecycle): Int {
            val p = if (other is PriorityApplicationWrapper) {
                other.priority
            } else {
                0
            }
            return priority - p
        }

    }

    private val mWrappers = ArrayList<PriorityApplicationWrapper>(8)

    /**
     * 初始化，从Manifest声明的[Application]读取其类的注解信息
     * 目的是为了将所有声明过的[AbsApplicationWrapper]读取到[mWrappers]
     * 中，便于在相关生命周期中进行调用管理
     */
    fun install(application: Application) {
        initAllWrappers(application)
    }

    /**
     * 从运行时的类开始读取类注解，然后再读取父类的注解
     */
    private fun initAllWrappers(application: Application) {
        var clazz: Class<in Application> = application.javaClass
        do {
            if (clazz.isAnnotationPresent(Kiss::class.java) || clazz.isAnnotationPresent(Kisses::class.java)) {
                val annotations = clazz.annotations
                for (annotation in annotations) {
                    if (annotation is Kisses) {
                        for (kiss in annotation.value) {
                            injectApplicationWrapperFromKiss(kiss, application)
                        }
                    } else if (annotation is Kiss) {
                        injectApplicationWrapperFromKiss(annotation, application)
                    }
                }
            }
            clazz = clazz.superclass ?: break
        } while (clazz != Application::class.java)
        mWrappers.sort()
    }

    /**
     * 从标记的[Kiss]注解中读取信息到[mWrappers]中存储
     */
    private fun injectApplicationWrapperFromKiss(kiss: Kiss, application: Application) {
        val wrapperClazz = kiss.value
        val priority = kiss.priority
        val constructor = wrapperClazz.java.getConstructor(Application::class.java)
        val wrapperImpl = constructor.newInstance(application)
        mWrappers.add(PriorityApplicationWrapper(wrapperImpl, priority))
        Timber.d("install ApplicationWrapper<${wrapperImpl.javaClass.name}> success!")
    }

    override fun attachBaseContext() {
        for (wrapper in mWrappers) {
            wrapper.attachBaseContext()
        }
    }

    override fun onCreate() {
        for (wrapper in mWrappers) {
            wrapper.onCreate()
        }
    }

    override fun onTerminate() {
        for (wrapper in mWrappers) {
            wrapper.onTerminate()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        for (wrapper in mWrappers) {
            wrapper.onConfigurationChanged(newConfig)
        }
    }

    override fun onLowMemory() {
        for (wrapper in mWrappers) {
            wrapper.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        for (wrapper in mWrappers) {
            wrapper.onTrimMemory(level)
        }
    }

}