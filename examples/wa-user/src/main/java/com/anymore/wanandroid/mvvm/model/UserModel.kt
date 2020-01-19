package com.anymore.wanandroid.mvvm.model

import android.app.Application
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.api.WanAndroidUserApi
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.base.WanAndroidResponse
import com.anymore.wanandroid.repository.database.AppDatabase
import com.anymore.wanandroid.repository.database.entry.UserInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * 用户管理model层，与用户信息有关的操作在这里
 * Created by liuyuanmao on 2019/3/28.
 */
class UserModel @Inject constructor(application: Application) : BaseModel(application) {

    private val mUserApi by lazy {
        mRepositoryComponent.getRepository().obtainRetrofitService(
            WAN_ANDROID_KEY,
            WanAndroidUserApi::class.java
        )
    }
    private val mAppDatabase by lazy {
        mRepositoryComponent.getRepository().obtainRoomDatabase(
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
    }
    private val mUserDao by lazy { mAppDatabase.userInfoDao() }

    fun register(
        username: String,
        pwd: String,
        rePwd: String
    ): Observable<WanAndroidResponse<UserInfo>> = mUserApi
        .register(username, pwd, rePwd)
        .subscribeOn(Schedulers.io())
        .doOnNext {
            if (it.errorCode == ResponseCode.OK && it.data != null) {
                mUserDao.insert(it.data!!)
            }
        }
        .observeOn(AndroidSchedulers.mainThread())

    suspend fun login(username: String, pwd: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            val response = mUserApi.login(username, pwd)
            Timber.d("thread-name:${Thread.currentThread().name}")
            var user: UserInfo? = null
            if (response.errorCode == ResponseCode.OK && response.data != null) {
                user = response.data
                user?.online = true
            }
            user
        }
    }

//    fun login(username: String,pwd: String):Observable<WanAndroidResponse<UserInfo>> = mUserApi
//        .login(username,pwd)
//        .subscribeOn(Schedulers.io())
//        .doOnNext{
//            Timber.d("mRepositoryComponent:${mRepositoryComponent.hashCode()}")
//            if (it.errorCode == ResponseCode.OK && it.data!=null){
//                it.data.online = true
//                mUserDao.insert(it.data)
//            }
//        }
//        .observeOn(AndroidSchedulers.mainThread())

    fun logout(): Observable<WanAndroidResponse<String>> = mUserApi
        .logout()
        .subscribeOn(Schedulers.io())
        .doOnNext {
            if (it.errorCode == ResponseCode.OK && it.data != null) {
                mUserDao.updateOnlineStatus(false)
            }
        }
        .observeOn(AndroidSchedulers.mainThread())

    fun getCurrentUser() = mUserDao.getCurrentUser().subscribeOn(Schedulers.io())

}