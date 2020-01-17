package com.anymore.andkit.mvp

import android.app.Application
import androidx.annotation.StringRes
import com.anymore.andkit.repository.di.component.RepositoryComponent
import com.anymore.andkit.repository.repositoryComponent

/**
 * M层基类，封装了取全局仓储层对象的操作(通过[BaseModel.mRepositoryComponent]获取仓储层对象管理器)
 * Created by liuyuanmao on 2019/7/16.
 */
open class BaseModel(protected val mApplication: Application): BaseContract.IBaseModel{

    protected val mRepositoryComponent: RepositoryComponent = mApplication.repositoryComponent

    protected fun getString(@StringRes stringId:Int):String=mApplication.getString(stringId)
}