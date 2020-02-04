package com.anymore.wanandroid.mvp.model

import android.app.Application
import com.anymore.andkit.mvp.BaseModel
import com.anymore.andkit.repository.repositoryComponent
import com.anymore.wanandroid.api.WanAndroidTodoApi
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.mvp.contract.TodoContract
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.exception.WanAndroidException
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by anymore on 2020/2/2.
 */
class TodoModel @Inject constructor(mApplication: Application) : BaseModel(mApplication),
    TodoContract.ITodoModel {

    private val repository by lazy { mApplication.repositoryComponent.getRepository() }
    private val api by lazy { repository.obtainRetrofitService(WAN_ANDROID_KEY,WanAndroidTodoApi::class.java) }


    override fun saveTodo(title:String,content:String,date: String,type:Int,priority:Int): Observable<Pair<Int, String>> {
        return api.createTodo(title,content,date,type,priority)
            .map {
                if (it.errorCode == ResponseCode.OK){
                    return@map Pair(0,"创建成功!")
                }else{
                    return@map Pair(-1,it.errorMsg?:"创建失败")
                }
            }
    }

    override fun updateTodo(
        id: Int,
        title: String,
        content: String,
        date: String,
        type: Int,
        priority: Int
    ): Observable<Pair<Int, String>> {
        return api.updateTodo(id,title,content,date,type,priority)
            .map {
                if (it.errorCode == ResponseCode.OK){
                    return@map Pair(0,"更新成功!")
                }else{
                    return@map Pair(-1,it.errorMsg?:"更新失败")
                }
            }
    }

    override fun deleteTodo(id: Int): Observable<Pair<Int, String>> {
        return api.deleteTodo(id)
            .map {
                if (it.errorCode == ResponseCode.OK){
                    return@map Pair(0,"删除成功!")
                }else{
                    return@map Pair(-1,it.errorMsg?:"删除失败")
                }
            }
    }

    override fun loadTodoList(
        page: Int,
        status: Int?,
        type: Int?,
        priority: Int?,
        orderby: Int?
    ): Observable<PagedData<Todo>> {
        val params = HashMap<String,Any>()
        status?.let {params["status"] = it}
        type?.let { params["type"] = it }
        priority?.let { params["priority"] = it }
        orderby?.let { params["orderby"] = it }
        return api.getTodoList(page,params)
            .map {
                if (it.errorCode == ResponseCode.OK && it.data != null){
                    return@map it.data!!
                }else{
                    Timber.e("获取待办列表失败:${it.errorMsg}")
                    throw WanAndroidException("获取待办列表失败!")
                }
            }
    }

    override fun updateTodoStatus(id: Int, newStatus: Int): Observable<Pair<Int, String>> {
        return api.updateTodoStatus(id,newStatus)
            .map {
                if (it.errorCode == ResponseCode.OK){
                    return@map Pair(0,"更新成功!")
                }else{
                    return@map Pair(-1,it.errorMsg?:"更新失败")
                }
            }
    }
}