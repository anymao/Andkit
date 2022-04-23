package com.anymore.wanandroid.frame.router

/**
 * Created by anymore on 2022/4/7.
 */
object WanAndroidRouter {

    const val scheme = "app"
    const val host = "m.wan-android.com"

    const val ExtraParam = "WanAndroidExtraParam"

    const val naScheme = ".*"
    const val naHost = ".*"

    private const val loginGroup = "login"
    const val splash = "/$loginGroup/splash"
    const val login = "/$loginGroup/login"
    const val register = "/$loginGroup/register"

    private const val mainGroup = "main"
    const val main = "/$mainGroup/main"

    private const val webGroup = "web"
    const val web = "/$webGroup/web"

    private const val articleGroup = "articles"
    const val articlesFragment = "/$articleGroup/articles_fragment"


    const val flutterScheme = "flutter"
    const val flutterHost = naHost


}