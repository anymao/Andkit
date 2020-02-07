package com.anymore.wanandroid.mvp.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseFragment
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.route.*
import com.anymore.wanandroid.route.service.UserService
import kotlinx.android.synthetic.main.wm_fragment_mine.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by anymore on 2020/1/29.
 */
class MineFragment : BaseFragment(),CoroutineScope {

    @JvmField
    @Autowired
    var mUserService: UserService? = null


    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main.immediate

    override fun getLayoutRes(): Int {
        ARouter.getInstance().inject(this)
        return R.layout.wm_fragment_mine
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        tvTodo.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_TODO_LIST)
                .navigation(requireContext())
        }
        tvCollected.setOnClickListener {
            ARouter.getInstance()
                .build(BROWSE_FRAGMENT)
                .withString("title", "收藏的文章")
                .withString("fragmentName", CollectedArticlesFragment::class.java.name)
                .withInt(EXTRA_NEED_LOGIN, NEED_LOGIN_FLAG)
                .navigation(requireContext())
        }
        tvGirls.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_FLUTTER)
                .withString("initialRoute", "/welfare")
                .navigation(requireContext())
        }
        tvAbout.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_FLUTTER)
                .withString("initialRoute", "/about")
                .navigation(requireContext())

        }

        tvLogout.setOnClickListener {
            mJob = launch {
                showSuccess("正在注销...")
                val response = mUserService?.logout()
                showSuccess("注销成功")
                if (response != null){
                    showSuccess(response.second)
                }
            }
        }
    }

    override fun onDestroy() {
        if (this::mJob.isInitialized){
            mJob.cancel()
        }
        super.onDestroy()
    }
}