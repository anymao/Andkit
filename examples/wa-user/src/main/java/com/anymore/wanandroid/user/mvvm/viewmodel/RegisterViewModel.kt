package com.anymore.wanandroid.user.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.anymore.andkit.dagger.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.andkit.mvvm.SingleLiveEvent
import com.anymore.wanandroid.common.event.LoadEvent
import com.anymore.wanandroid.common.event.LoadState
import com.anymore.wanandroid.user.mvvm.model.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by liuyuanmao on 2019/3/28.
 */
@ActivityScope
class RegisterViewModel @Inject constructor(
    application: Application,
    private val userModel: UserModel
) :
    BaseViewModel(application) {
    val mErrorMessage = SingleLiveEvent<String>()
    val mMessage = SingleLiveEvent<String>()
    val mLoadStateEvent by lazy { SingleLiveEvent<LoadEvent>() }
    val mRegisterEvent by lazy { SingleLiveEvent<Boolean>() }

    fun register(username: String?, pwd: String?, rePwd: String?) {
        if (checkUser(username, pwd, rePwd)) {
            viewModelScope.launch {
                mLoadStateEvent.postValue(LoadEvent(LoadState.START, "正在注册..."))
                val userinfo = userModel.register(username!!, pwd!!, rePwd!!)
                mLoadStateEvent.postValue(LoadEvent(LoadState.COMPLETED))
                if (userinfo != null) {
                    mMessage.value = "登录成功!"
                    mRegisterEvent.value = true
                } else {
                    mMessage.value = "登陆失败"
                }

            }
//            val disposable = userModel.register(username!!, pwd!!, rePwd!!)
//                .subscribeBy(onNext = {
//                    if (it.errorCode == ResponseCode.OK) {
//                        mMessage.value = "注册成功!"
//                    } else {
//                        mErrorMessage.value = it.errorMsg
//                    }
//                }, onError = {
//                    mErrorMessage.value = it.message
//                })
//            addToCompositeDisposable(disposable)
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


