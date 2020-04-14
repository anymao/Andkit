package com.anymore.wanandroid.mine.api

import androidx.annotation.IntRange
import androidx.annotation.NonNull
import com.anymore.wanandroid.mine.entry.Todo
import com.anymore.wanandroid.repository.base.PagedData
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by anymore on 2020/2/1.
 */
interface WanAndroidTodoApi {
    //新建一条todo
    @FormUrlEncoded
    @POST("/lg/todo/add/json")
    fun createTodo(
        @NonNull @Field("title") title: String,
        @NonNull @Field("content") content: String,
        @Field("date") date: String,
        @IntRange(from = 1) @Field("type") type: Int,
        @IntRange(from = 1) @Field("priority") priority: Int
    ): Observable<WanAndroidResponse<Any>>

    //更新一条todo
    @FormUrlEncoded
    @POST("/lg/todo/update/{id}/json")
    fun updateTodo(
        @Path("id") id: Int,
        @NonNull @Field("title") title: String,
        @NonNull @Field("content") content: String,
        @Field("date") date: String,
        @IntRange(from = 1) @Field("type") type: Int,
        @IntRange(from = 1) @Field("priority") priority: Int
    ): Observable<WanAndroidResponse<Any>>

    //删除todo
    @POST("/lg/todo/delete/{id}/json")
    fun deleteTodo(@Path("id") id: Int): Observable<WanAndroidResponse<Any>>

    @FormUrlEncoded
    @POST("/lg/todo/done/{id}/json")
    fun updateTodoStatus(@Path("id") id: Int, @Field("status") status: Int): Observable<WanAndroidResponse<Any>>

    @GET("/lg/todo/v2/list/{page}/json")
    fun getTodoList(
        @Path("page") page: Int,
        @QueryMap map: MutableMap<String, Any>
    ): Observable<WanAndroidResponse<PagedData<Todo>>>
}