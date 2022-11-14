package com.anymore.wanandroid.articles.view

import android.app.Application
import com.anymore.andkit.common.ktx.bg
import com.anymore.andkit.common.ktx.click
import com.anymore.andkit.common.livedata.NullSafetyLiveData
import com.anymore.andkit.core.ktx.launch
import com.anymore.andkit.mvvm.base.BaseDataBindingDialogFragment
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.andkit.mvvm.ktx.andkitViewModels
import com.anymore.wanandroid.articles.R
import com.anymore.wanandroid.articles.databinding.ArticlesFragmentArticlesBinding
import com.anymore.wanandroid.frame.router.WanAndroidRouter.articlesFragment
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naHost
import com.anymore.wanandroid.frame.router.WanAndroidRouter.naScheme
import com.anymore.wanandroid.repository.rpc.response.HomeBannerVo
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import com.didi.drouter.annotation.Router
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by anymore on 2022/4/9.
 */
@Router(scheme = naScheme, host = naHost, path = articlesFragment)
@AndroidEntryPoint
class ArticlesFragment : BaseDataBindingDialogFragment<ArticlesFragmentArticlesBinding>() {

    private val vm by andkitViewModels<ViewModel>()

    override fun getLayoutRes() = R.layout.articles_fragment_articles

    override fun initView() {
        super.initView()
        vm.banners.observe(this) {
            binding.text.text = it?.firstOrNull()?.title
        }
        binding.wrapper.loader = {
            launch(callback = binding.wrapper) {
                delay(5000)
                vm.loadBanners()
            }
        }
        binding.text.click {
            getData()
        }
    }

    override fun getData() {
        super.getData()
        binding.wrapper.load()
    }

    @HiltViewModel
    class ViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

        @Inject
        lateinit var service: WanAndroidService

        val banners = NullSafetyLiveData.empty<List<HomeBannerVo>>()

        suspend fun loadBanners() {
            banners.value = bg { service.getHomeBanners() }
        }
    }

}