package com.anymore.andkit.mvvm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anymore.andkit.common.livedata.NullSafeLiveData

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val toast = NullSafeLiveData.empty<CharSequence>()
    val successToast = NullSafeLiveData.empty<CharSequence>()
    val failedToast = NullSafeLiveData.empty<CharSequence>()
    val loading = NullSafeLiveData(false)
}