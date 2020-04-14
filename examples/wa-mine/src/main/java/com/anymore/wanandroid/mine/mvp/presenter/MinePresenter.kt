package com.anymore.wanandroid.mine.mvp.presenter

import android.app.Application
import com.anymore.andkit.mvp.BasePresenter
import com.anymore.wanandroid.common.ext.disk2Main
import com.anymore.wanandroid.mine.mvp.contract.MineContract
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by anymore on 2020/2/8.
 */
class MinePresenter @Inject constructor(
    application: Application,
    mView: MineContract.IMineView,
    private val mModel: MineContract.IMineModel
) :
    BasePresenter<MineContract.IMineView>(application, mView), MineContract.IMinePresenter,
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main.immediate

    private var job: Job? = null


    override fun getLoginStatus(): Boolean {
        return mModel.getLoginStatus()
    }

    override fun loadCurrentUserInfo() {
        val disposable = mModel.getCurrentUserInfo()
            .disk2Main()
            .subscribeBy(
                onError = {
                    mView.showUserInfo(null)
                },
                onSuccess = {
                    mView.showUserInfo(it)
                },
                onComplete = {
                    mView.showUserInfo(null)
                }
            )
        addDisposable(disposable)
    }

    override fun logout() {
        job = launch {
            mView.showProgressBar("正在注销...")
            val result = mModel.logout()
            mView.hideProgressBar()
            if (result.first == 0) {
                mView.logoutSuccess()
            } else {
                mView.showError(result.second)
            }
        }
    }

    override fun onDestroy() {
        super<BasePresenter>.onDestroy()
        job?.cancel()
    }
}