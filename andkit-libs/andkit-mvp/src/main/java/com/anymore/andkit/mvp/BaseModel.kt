package com.anymore.andkit.mvp

import android.app.Application
import androidx.annotation.StringRes
import com.anymore.andkit.repository.RepositoryManager
import javax.inject.Inject

/**
 * M层基类，封装了取全局仓储层对象的操作(通过[BaseModel.mRepositoryComponent]获取仓储层对象管理器)
 * Created by liuyuanmao on 2019/7/16.
 */
open class BaseModel(protected val mApplication: Application) : BaseContract.IBaseModel {

    @Inject
    lateinit var repositoryManager: RepositoryManager

    val dataCache by lazy { repositoryManager.obtainDataCache() }

    protected fun getString(@StringRes stringId: Int): String = mApplication.getString(stringId)
}