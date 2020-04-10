package com.anymore.andkit.mvvm

import android.app.Application
import com.anymore.andkit.repository.di.component.RepositoryComponent
import com.anymore.andkit.repository.repositoryComponent

/**
 * Created by liuyuanmao on 2019/2/23.
 */
open class BaseModel(mApplication: Application) : IModel {
    protected val mRepositoryComponent: RepositoryComponent = mApplication.repositoryComponent
}
