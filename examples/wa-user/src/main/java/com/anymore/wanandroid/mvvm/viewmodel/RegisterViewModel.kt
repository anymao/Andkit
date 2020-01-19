package com.anymore.wanandroid.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import com.anymore.andkit.lifecycle.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.andkit.mvvm.SingleLiveEvent
import com.anymore.wanandroid.mvvm.model.UserModel
import com.anymore.wanandroid.repository.base.ResponseCode
import io.reactivex.rxkotlin.subscribeBy

import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/3/28.
 */
@ActivityScope
class RegisterViewModel @Inject constructor(application: Application, private val userModel: UserModel) :
    BaseViewModel(application) {
    val mErrorMessage = SingleLiveEvent<String>()
    val mMessage = SingleLiveEvent<String>()
    fun register(username: String?, pwd: String?, rePwd: String?) {
        if (checkUser(username, pwd, rePwd)) {
            val disposable = userModel.register(username!!, pwd!!, rePwd!!)
                .subscribeBy(onNext = {
                    if (it.errorCode == ResponseCode.OK) {
                        mMessage.value = "注册成功!"
                    } else {
                        mErrorMessage.value = it.errorMsg
                    }
                }, onError = {
                    mErrorMessage.value = it.message
                })
            addToCompositeDisposable(disposable)
        }
    }

    private fun checkUser(username: String?, pwd: String?, rePwd: String?): Boolean {
        if (TextUtils.isEmpty(username)) {
            mErrorMessage.value = "用户名不能为空!"
            return false
        }
        if (TextUtils.isEmpty(pwd)) {
            mErrorMessage.value = "密码不能为空!"
            return false
        }
        if (TextUtils.isEmpty(rePwd)) {
            mErrorMessage.value = "确认密码不能为空!"
            return false
        }
        if (!TextUtils.equals(pwd, rePwd)) {
            mErrorMessage.value = "两次密码不一致!"
            return false
        }
        return true
    }
}


