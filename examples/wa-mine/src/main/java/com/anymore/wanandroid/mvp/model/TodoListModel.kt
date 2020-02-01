package com.anymore.wanandroid.mvp.model

import android.app.Application
import com.anymore.andkit.mvp.BaseModel
import com.anymore.andkit.repository.repositoryComponent
import com.anymore.wanandroid.api.WanAndroidTodoApi
import com.anymore.wanandroid.entry.TodoData
import com.anymore.wanandroid.mvp.contract.TodoListContract
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by anymore on 2020/1/30.
 */
class TodoListModel @Inject constructor(mApplication: Application) : BaseModel(mApplication),
    TodoListContract.ITodoListModel {

    private val repositoryManager by lazy { mApplication.repositoryComponent.getRepository() }
    private val mApi:WanAndroidTodoApi by lazy { repositoryManager.obtainRetrofitService(
        WAN_ANDROID_KEY,WanAndroidTodoApi::class.java) }


    override fun loadTodoList(
        page: Int,
        status: Int?,
        type: Int?,
        priority: Int?,
        orderby: Int?
    ): Observable<TodoData> {
        val params = HashMap<String,Any>()
        status?.let {params["status"] = it}
        type?.let { params["type"] = it }
        priority?.let { params["priority"] = it }
        orderby?.let { params["orderby"] = it }
        return mApi.getTodoList(page,params)
            .map {
                if (it.errorCode == ResponseCode.OK && it.data != null){
                    return@map it.data!!
                }else{
                    Timber.e("获取待办列表失败:${it.errorMsg}")
                    throw WanAndroidException("获取待办列表失败!")
                }
            }
    }

}