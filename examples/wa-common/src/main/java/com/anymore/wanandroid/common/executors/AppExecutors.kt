package com.anymore.wanandroid.common.executors

import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.anymore.wanandroid.common.ContextProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 应用线程池管理
 * Created by anymore on 2019/5/20.
 */
object AppExecutors {
    val diskIoExecutor: Executor by lazy { Executors.newSingleThreadExecutor() }
    val networkExecutor: Executor by lazy { Executors.newFixedThreadPool(3) }
    val mainExecutor: Executor by lazy { ContextCompat.getMainExecutor(ContextProvider.getApplicationContext()) }
    val mainHandler by lazy { Handler(Looper.getMainLooper()) }
    val diskIoScheduler: Scheduler
        get() = Schedulers.from(diskIoExecutor)

    val networkScheduler: Scheduler
        get() = Schedulers.from(networkExecutor)

    val mainScheduler: Scheduler
        get() = Schedulers.from(mainExecutor)

}
