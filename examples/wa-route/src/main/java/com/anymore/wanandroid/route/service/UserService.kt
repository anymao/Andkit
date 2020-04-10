package com.anymore.wanandroid.route.service

import com.alibaba.android.arouter.facade.template.IProvider
import com.anymore.wanandroid.repository.database.entry.UserInfo
import io.reactivex.Maybe

/**
 * Created by anymore on 2020/2/7.
 */
interface UserService : IProvider {
    fun setLoginStatus(isLogin: Boolean)
    fun getLoginStatus(): Boolean
    fun getCurrentUser(): Maybe<UserInfo>
    suspend fun login(username: String, password: String): UserInfo?
    suspend fun logout(): Pair<Int, String>
}