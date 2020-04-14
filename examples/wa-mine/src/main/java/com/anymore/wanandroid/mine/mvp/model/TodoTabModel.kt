package com.anymore.wanandroid.mine.mvp.model

import android.app.Application
import com.anymore.andkit.mvp.BaseModel
import com.anymore.wanandroid.mine.mvp.contract.TodoTabContract
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/29.
 */
class TodoTabModel @Inject constructor(mApplication: Application) : BaseModel(mApplication),
    TodoTabContract.ITodoTabModel {

}