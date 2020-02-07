package com.anymore.wanandroid.mvvm.model

import android.app.Application
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvvm.BaseModel
import com.anymore.wanandroid.api.WanAndroidUserApi
import com.anymore.wanandroid.repository.WAN_ANDROID_KEY
import com.anymore.wanandroid.repository.base.ResponseCode
import com.anymore.wanandroid.repository.database.AppDatabase
import com.anymore.wanandroid.repository.database.entry.UserInfo
import com.anymore.wanandroid.route.service.UserService
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

    @Autowired
    lateinit var mUserService: UserService

    init {
        ARouter.getInstance().inject(this)
    }

    suspend fun register(username: String, pwd: String, rePwd: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            val response = mUserApi.register(username, pwd, rePwd)
            var user: UserInfo? = null
            if (response.errorCode == ResponseCode.OK && response.data != null) {
                user = response.data
                user?.online = true
                mUserDao.insert(user!!)
                mUserService.setLoginStatus(true)
            }
            user
        }
    }

//    fun register(
//        username: String,
//        pwd: String,
//        rePwd: String
//    ): Observable<WanAndroidResponse<UserInfo>> = mUserApi
//        .register(username, pwd, rePwd)
//        .subscribeOn(Schedulers.io())
//        .doOnNext {
//            if (it.errorCode == ResponseCode.OK && it.data != null) {
//                mUserDao.insert(it.data!!)
//            }
//        }
//        .observeOn(AndroidSchedulers.mainThread())

    suspend fun login(username: String, pwd: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            val response = mUserApi.login(username, pwd)
            Timber.d("thread-name:${Thread.currentThread().name}")
            var user: UserInfo? = null
            if (response.errorCode == ResponseCode.OK && response.data != null) {
                user = response.data
                user?.online = true
                mUserDao.insert(user!!)
                mUserService.setLoginStatus(true)
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

    suspend fun logout(): Pair<Int, String> {
        return withContext(Dispatchers.IO) {
            val response = mUserApi.logout()
            val code = response.errorCode
            if (code == ResponseCode.OK) {
                mUserDao.deleteAll()
                return@withContext Pair(0, "注销成功")
            } else {
                return@withContext Pair(code, response.errorMsg ?: "注销失败")
            }

        }
    }

//    fun logout(): Observable<WanAndroidResponse<String>> = mUserApi
//        .logout()
//        .subscribeOn(Schedulers.io())
//        .doOnNext {
//            if (it.errorCode == ResponseCode.OK && it.data != null) {
//                mUserDao.updateOnlineStatus(false)
//            }
//        }
//        .observeOn(AndroidSchedulers.mainThread())

    fun getCurrentUser() = mUserDao.getCurrentUser().subscribeOn(Schedulers.io())

}