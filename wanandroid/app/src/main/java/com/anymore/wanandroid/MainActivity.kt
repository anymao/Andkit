package com.anymore.wanandroid

import androidx.fragment.app.Fragment
import com.anymore.andkit.mvvm.base.BaseDataBindingActivity
import com.anymore.wanandroid.databinding.ActivityMainBinding
import com.anymore.wanandroid.frame.ktx.routeFragment
import com.anymore.wanandroid.frame.router.WanAndroidRouter.articlesFragment
import com.anymore.wanandroid.frame.router.WanAndroidRouter.main
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import com.anymore.wanandroid.widget.adapter.FragmentAdapter
import com.didi.drouter.annotation.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Router(scheme = naScheme, host = naHost, path = main)
class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {

    @Inject
    lateinit var service: WanAndroidService

    override fun getLayoutRes() = R.layout.activity_main


    override fun initView() {
        super.initView()

        val adapter = FragmentAdapter(
            mutableListOf(
                provideFragmentCreator(),
                provideFragmentCreator(),
                provideFragmentCreator()
            ),
            supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }

    private fun provideFragmentCreator(): () -> Fragment {
        return {
            routeFragment(articlesFragment)
        }
    }

}