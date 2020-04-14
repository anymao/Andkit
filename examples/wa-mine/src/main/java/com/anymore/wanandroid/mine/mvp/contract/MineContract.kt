package com.anymore.wanandroid.mine.mvp.contract

import com.anymore.andkit.mvp.BaseContract
import com.anymore.wanandroid.repository.database.entry.UserInfo
import io.reactivex.Maybe

/**
 * Created by anymore on 2020/2/8.
 */
interface MineContract {
    interface IMineView : BaseContract.IBaseView {
        fun showUserInfo(user: UserInfo?)
        fun logoutSuccess()
    }

    interface IMinePresenter : BaseContract.IBasePresenter {
        fun getLoginStatus(): Boolean
        fun loadCurrentUserInfo()
        fun logout()
    }

    interface IMineModel : BaseContract.IBaseModel {
        fun getLoginStatus(): Boolean
        fun getCurrentUserInfo(): Maybe<UserInfo>
        suspend fun logout(): Pair<Int, String>
    }
}