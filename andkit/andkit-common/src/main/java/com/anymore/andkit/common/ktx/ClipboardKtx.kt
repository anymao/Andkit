package com.anymore.andkit.common.ktx

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


/**
 * 剪切板相关的扩展工具
 */
/**
 *获取/设置 剪切板数据
 */
var clipboardContent: String?
    set(value) {
        tryOrNothing {
            val cm =
                applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                    ?: return
            val data = ClipData.newPlainText("Copied Text", value)
            cm.setPrimaryClip(data)
        }
    }
    get() = tryOrNull {
        val cm =
            applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                ?: return null
        val data = cm.primaryClip ?: return null
        if (data.itemCount > 0) {
            val item = data.getItemAt(0)
            return item.coerceToText(applicationContext)?.toString()
        }
        return null
    }

/**
 * 清空剪切板数据
 */
fun clearClipboard() {
    tryOrNothing {
        val cm =
            applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                ?: return
        val data = ClipData.newPlainText("", "")
        cm.setPrimaryClip(data)
    }
}