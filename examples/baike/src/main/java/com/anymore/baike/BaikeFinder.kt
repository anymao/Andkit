package com.anymore.baike

import androidx.annotation.WorkerThread
import com.anymore.baike.bean.BaikeResult
import org.jsoup.Jsoup
import timber.log.Timber
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

/**
 * Created by lym on 2020/11/13.
 */
@OptIn(ExperimentalTime::class)
object BaikeFinder {
    private const val BaiduBaikeUrl = "https://baike.baidu.com/item/"

    @WorkerThread
    fun find(keywords: String): BaikeResult {
        Timber.d("Baidu baike find:$keywords")
        return measureTimedValue {
            try {
                val url = "$BaiduBaikeUrl$keywords"
                val document = Jsoup.connect(url).get()
                val success = document.toString().contains("<!--STATUS OK-->", ignoreCase = true)
                if (success) {
                    val content = document.head().select("meta[name=description]").firstOrNull()
                        ?.attr("content")
                    BaikeResult(BaikeResult.SUCCESS, keywords, content, null, url)
                } else {
                    BaikeResult(BaikeResult.NOT_FOUND, keywords, null, null, null)
                }
            } catch (e: Exception) {
                Timber.e(e, "BaikeFinder find Error:")
                BaikeResult(BaikeResult.NETWORK_FAILED, keywords, null, null, null)
            }
        }.let {
            Timber.d("Baidu baike find:${it.value} with time<${it.duration.inMilliseconds}>")
            it.value
        }
    }
}