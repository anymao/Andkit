package com.anymore.wanandroid

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.AndkitApplication
import com.anymore.andkit.annotations.Kiss
import com.anymore.andkit.annotations.Kisses
import com.anymore.andkit.dagger.module.ApplicationModule
import com.anymore.wanandroid.articles.ArticlesApplicationWrapper
import com.anymore.wanandroid.di.component.DaggerWanAndroidComponent
import com.anymore.wanandroid.mine.MineApplicationWrapper
import com.anymore.wanandroid.user.BuildConfig
import com.anymore.wanandroid.user.UserApplicationWrapper
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader


/**
 * Created by liuyuanmao on 2020/1/17.
 */
@Kisses(
    value = [
        Kiss(MineApplicationWrapper::class, priority = 99),
        Kiss(UserApplicationWrapper::class, priority = 98),
        Kiss(ArticlesApplicationWrapper::class, priority = 97)
    ]
)
class WanAndroidApplication : AndkitApplication() {

    companion object {
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.wa_colorPrimary, android.R.color.white)//全局设置主题颜色
                ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DaggerWanAndroidComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

}