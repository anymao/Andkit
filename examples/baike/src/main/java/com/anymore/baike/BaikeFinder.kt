package com.anymore.baike

import androidx.annotation.WorkerThread
import com.anymore.baike.bean.BaikeResult
import org.jsoup.Jsoup
import timber.log.Timber

/**
 * Created by lym on 2020/11/13.
 */
object BaikeFinder {
    private const val BaiduBaikeUrl = "https://baike.baidu.com/item/"

    @WorkerThread
    fun find(keywords:String):BaikeResult{
        return try {
            val url = "$BaiduBaikeUrl$keywords"
            val document = Jsoup.connect(url).get()
            val success = document.toString().apply { Timber.e(this) }.contains("<!--STATUS OK-->",ignoreCase = true)
            if (success){
                val content = document.head().select("meta[name=description]").firstOrNull()?.attr("content")
                BaikeResult(BaikeResult.SUCCESS,keywords,content,null,url)
            }else{
                BaikeResult(BaikeResult.NOT_FOUND,keywords,null,null,null)
            }
        }catch (e:Exception){
            Timber.e(e,"BaikeFinder find Error:")
            BaikeResult(BaikeResult.NETWORK_FAILED,keywords,null,null,null)
        }
    }
}