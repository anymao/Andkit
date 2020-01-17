package com.anymore.wanandroid.common.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 应用线程池管理
 * Created by anymore on 2019/5/20.
 */
object AppExecutors{
    val diskIoExecutor :Executor by lazy { Executors.newSingleThreadExecutor() }
    val networkExecutor:Executor by lazy { Executors.newFixedThreadPool(3) }
    val mainExecutor:Executor by lazy {
        object :Executor{
            private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
            override fun execute(command: Runnable?) {
                command?.let { mainHandler.post(it) }
            }
        }
    }
}
