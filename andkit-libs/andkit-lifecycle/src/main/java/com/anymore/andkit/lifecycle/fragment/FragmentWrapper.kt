package com.anymore.andkit.lifecycle.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * Created by liuyuanmao on 2019/2/23.
 */
class FragmentWrapper(
    private val mFragment: Fragment,
    private val iFragment: IFragment
) :
    IFragmentLifecycle {
    override fun onAttach(context: Context) {
        Timber.i("onAttach")
        if (iFragment.useEventBus()){
            EventBus.getDefault().register(mFragment)
        }
        if (iFragment.injectable()){
            AndroidSupportInjection.inject(mFragment)
        }
    }


    override fun onActivityCreate(savedInstanceState: Bundle?) {
        Timber.i("onActivityCreate")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("FragmentWrapper onResume")
    }

    override fun onPause() {
        Timber.i("FragmentWrapper onPause")
        super.onPause()
    }

    override fun onDetach() {
        if (iFragment.useEventBus()){
            EventBus.getDefault().unregister(mFragment)
        }
    }

    override fun isAdded()= mFragment.isAdded
}