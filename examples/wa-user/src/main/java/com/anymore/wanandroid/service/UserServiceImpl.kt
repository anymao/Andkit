package com.anymore.wanandroid.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.anymore.andkit.repository.IRepositoryManager
import com.anymore.andkit.repository.repositoryComponent
import com.anymore.wanandroid.api.WanAndroidUserApi
import com.anymore.wanandroid.common.ContextProvider
import com.anymore.wanandroid.common.delegates.SharedPreferenceHelper
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.database.AppDatabase
import com.anymore.wanandroid.repository.database.UserInfoDao
import com.anymore.wanandroid.repository.database.entry.UserInfo
import com.anymore.wanandroid.route.USER_SERVICE
import com.anymore.wanandroid.route.service.UserService
import io.reactivex.Maybe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by anymore on 2020/2/7.
 */
@Route(path = USER_SERVICE)
class UserServiceImpl : UserService {

    companion object{
        const val extra_login_status = "extra_login_status_key"
    }

    private lateinit var mContext: Context
    private lateinit var repository: IRepositoryManager
    private lateinit var userApi: WanAndroidUserApi
    private lateinit var userDao: UserInfoDao
    private val spHelper by lazy { SharedPreferenceHelper() }

    override fun init(context: Context?) {
        mContext = context ?: ContextProvider.getApplicationContext()
        repository = mContext.repositoryComponent.getRepository()
        userApi = repository.obtainRetrofitService(WAN_ANDROID_KEY, WanAndroidUserApi::class.java)
        userDao = repository.obtainRoomDatabase(AppDatabase::class.java, AppDatabase.DB_NAME)
            .userInfoDao()

    }

    override fun setLoginStatus(isLogin: Boolean) {
        val helper = SharedPreferenceHelper()
        helper.put(extra_login_status, isLogin)
    }

    override fun getLoginStatus(): Boolean {
        return spHelper.get(extra_login_status, false)
    }

    override fun getCurrentUser(): Maybe<UserInfo> {
        return userDao.getCurrentUser()
    }

    override suspend fun login(username: String, password: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            val response = userApi.login(username, password)
            Timber.d("thread-name:${Thread.currentThread().name}")
            var user: UserInfo? = null
            if (response.errorCode == ResponseCode.OK && response.data != null) {
                user = response.data
                user?.online = true
                setLoginStatus(true)
            }
            user
        }
    }

    override suspend fun logout(): Pair<Int, String> {
        return withContext(Dispatchers.IO) {
            val response = userApi.logout()
            val code = response.errorCode
            if (code == ResponseCode.OK) {
                setLoginStatus(false)
                userDao.deleteAll()
                return@withContext Pair(0, "注销成功")
            } else {
                return@withContext Pair(code, response.errorMsg ?: "注销失败")
            }

        }
    }
}