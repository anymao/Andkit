package com.anymore.wanandroid.mine.mvp.model

import android.app.Application
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseModel
import com.anymore.wanandroid.mine.mvp.contract.MineContract
import com.anymore.wanandroid.repository.database.entry.UserInfo
import com.anymore.wanandroid.route.service.UserService
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by anymore on 2020/2/8.
 */
class MineModel @Inject constructor(mApplication: Application) : BaseModel(mApplication),
    MineContract.IMineModel {

    @JvmField
    @Autowired
    var mUserService: UserService? = null

    init {
        ARouter.getInstance().inject(this)
    }

    override fun getLoginStatus(): Boolean {
        return mUserService?.getLoginStatus() ?: false
    }

    override fun getCurrentUserInfo(): Maybe<UserInfo> {
        return mUserService?.getCurrentUser()
            ?: Maybe.error(Throwable("错误，模块未实现<${UserService::class.java.simpleName}>"))
    }

    override suspend fun logout(): Pair<Int, String> {
        if (mUserService?.getLoginStatus() != true) {
            return Pair(-1, "用户未登录")
        }
        return mUserService?.logout() ?: Pair(-1, "错误，模块未实现<${UserService::class.java.simpleName}>")
    }
}