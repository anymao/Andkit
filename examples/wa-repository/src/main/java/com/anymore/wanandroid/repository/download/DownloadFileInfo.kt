package com.anymore.wanandroid.repository.download

import android.os.Parcelable
import com.blankj.utilcode.util.FileUtils
import com.liulishuo.okdownload.core.cause.EndCause
import kotlinx.android.parcel.Parcelize

/**
 * Created by anymore on 2020/11/21.
 */
@Parcelize
data class DownloadFileInfo(
    val filePath: String?,
    val downloadUrl: String,
    val downloadResult: EndCause,
    val action: AfterDownloadAction,
    val fileName: String? = if (FileUtils.isFileExists(filePath)) {
        FileUtils.getFileName(filePath)
    } else {
        null
    },
) : Parcelable