package com.anymore.wanandroid.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.anymore.andkit.lifecycle.scope.ActivityScope
import com.anymore.andkit.mvvm.BaseViewModel
import com.anymore.andkit.mvvm.SingleLiveEvent
import com.anymore.wanandroid.common.event.LoadEvent
import com.anymore.wanandroid.common.event.LoadState
import com.anymore.wanandroid.mvvm.model.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * a test account:
 * username:anymao
 * password：***********
 * Created by liuyuanmao on 2019/3/29.
 */
@ActivityScope
class LoginViewModel @Inject constructor(application: Application,private val userModel: UserModel) :
    BaseViewModel(application) {

    val mLoginEvent by lazy { SingleLiveEvent<Boolean>() }
    val mLoadStateEvent by lazy { SingleLiveEvent<LoadEvent>() }
    val toastEvent by lazy { SingleLiveEvent<CharSequence>() }

    fun login(username: String?, pwd: String?) {
        if (checkUser(username, pwd)) {
            viewModelScope.launch {
                mLoadStateEvent.postValue(LoadEvent(LoadState.START,"正在登录..."))
                val user = userModel.login(username!!,pwd!!)
                mLoadStateEvent.postValue(LoadEvent(LoadState.COMPLETED))
                if (user != null){
                    toastEvent.value = "登录成功!"
                    mLoginEvent.value = true
                }else{
                    toastEvent.value = "登陆失败"
                }

            }
        }
    }

    private fun checkUser(username: String?, pwd: String?): Boolean {
        if (TextUtils.isEmpty(username)) {
            toastEvent.value = "用户名不能为空!"
            return false
        }
        if (TextUtils.isEmpty(pwd)) {
            toastEvent.value = "密码不能为空!"
            return false
        }
        return true
    }
}