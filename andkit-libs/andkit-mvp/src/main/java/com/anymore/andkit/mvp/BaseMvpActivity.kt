package com.anymore.andkit.mvp

import android.os.Bundle
import javax.inject.Inject

/**
 * Activity作为V层时的基类，封装了注入Presenter操作，通过泛型指定Presenter层接口，V层和P层以接口调用
 * 互不关心具体实现
 * Created by liuyuanmao on 2019/7/16.
 */
abstract class BaseMvpActivity<P : BaseContract.IBasePresenter> : BaseActivity() {

    @Inject
    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mPresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mPresenter)
    }

    override fun injectable() = true
}