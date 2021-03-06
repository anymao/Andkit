package com.anymore.wanandroid.mine.mvp.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpFragment
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mine.mvp.contract.MineContract
import com.anymore.wanandroid.repository.database.entry.UserInfo
import com.anymore.wanandroid.route.*
import kotlinx.android.synthetic.main.wm_fragment_mine.*

/**
 * Created by anymore on 2020/1/29.
 */
@Route(path = MINE_MINE_FRAGMENT)
class MineFragment : BaseMvpFragment<MineContract.IMinePresenter>(), MineContract.IMineView {

    override fun getLayoutRes() = R.layout.wm_fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        tvUserInfo.setOnClickListener {
            if (!mPresenter.getLoginStatus()) {
                ARouter.getInstance()
                    .build(USER_LOGIN)
                    .navigation(requireContext())
            }
        }
        tvTodo.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_TODO_LIST)
                .navigation(requireContext())
        }
        tvCollected.setOnClickListener {
            ARouter.getInstance()
                .build(BROWSE_FRAGMENT)
                .withString(TITLE, "收藏的文章")
                .withString(FRAGMENT_ROUTE, MINE_COLLECTED_ARTICLES_FRAGMENT)
                .withInt(EXTRA_NEED_LOGIN, NEED_LOGIN_FLAG)
                .navigation(requireContext())
        }
        tvGirls.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_FLUTTER)
                .withString(FLUTTER_INITIAL_ROUTE, "/welfare")
                .navigation(requireContext())
        }
        tvAbout.setOnClickListener {
            ARouter.getInstance()
                .build(MINE_FLUTTER)
                .withString(FLUTTER_INITIAL_ROUTE, "/about")
                .navigation(requireContext())

        }

        tvLogout.setOnClickListener {
            mPresenter.logout()
        }
        mPresenter.loadCurrentUserInfo()
    }

    override fun showUserInfo(user: UserInfo?) {
        if (user != null) {
            tvUserInfo.text = user.nickname
        } else {
            tvUserInfo.text = "未登录（点击登录）"
        }
    }

    override fun logoutSuccess() {
        showSuccess("注销成功")
        mPresenter.loadCurrentUserInfo()
    }
}