package com.anymore.andkit.mvvm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anymore.andkit.common.livedata.NullSafetyLiveData

/**
 * Created by anymore on 2022/3/29.
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val toast = NullSafetyLiveData.empty<CharSequence>()
    val successToast = NullSafetyLiveData.empty<CharSequence>()
    val failedToast = NullSafetyLiveData.empty<CharSequence>()
    val loading = NullSafetyLiveData(false)
}