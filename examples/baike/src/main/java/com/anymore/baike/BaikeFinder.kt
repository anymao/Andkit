package com.anymore.baike

import androidx.annotation.WorkerThread
import com.anymore.baike.bean.BaikeResult
import org.jsoup.Jsoup

/**
 * Created by lym on 2020/11/13.
 */
object BaikeFinder {
    private const val BaiduBaikeUrl = "https://baike.baidu.com/item/"

    @WorkerThread
    fun find(keywords:String):BaikeResult?{
        val document = Jsoup.connect("$BaiduBaikeUrl$keywords").get()

        return null
    }
}