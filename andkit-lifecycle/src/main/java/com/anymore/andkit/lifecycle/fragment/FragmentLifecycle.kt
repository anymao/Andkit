package com.anymore.andkit.lifecycle.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {
    private val mFragmentWrapperMap = HashMap<Fragment, FragmentWrapper>()

    override fun onFragmentAttached(
        fm: FragmentManager,
        f: Fragment,
        context: Context
    ) {
        super.onFragmentAttached(fm, f, context)
        if (f is IFragment) {
            var fragmentWrapper = mFragmentWrapperMap[f]
            if (fragmentWrapper == null || !fragmentWrapper.isAdded()) {
                fragmentWrapper = FragmentWrapper(f, f)
                mFragmentWrapperMap[f] = fragmentWrapper
            }
            fragmentWrapper.onAttach(context)
        }
    }

    override fun onFragmentCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        mFragmentWrapperMap[f]?.onCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        mFragmentWrapperMap[f]?.onCreateView(v, savedInstanceState)
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        mFragmentWrapperMap[f]?.onActivityCreate(savedInstanceState)
    }

    override fun onFragmentStarted(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentStarted(fm, f)
        mFragmentWrapperMap[f]?.onStart()
    }

    override fun onFragmentResumed(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentResumed(fm, f)
        mFragmentWrapperMap[f]?.onResume()
    }

    override fun onFragmentPaused(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentPaused(fm, f)
        mFragmentWrapperMap[f]?.onPause()
    }

    override fun onFragmentStopped(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentStopped(fm, f)
        mFragmentWrapperMap[f]?.onStop()
    }

    override fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        f: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        mFragmentWrapperMap[f]?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentViewDestroyed(fm, f)
        mFragmentWrapperMap[f]?.onDestroyView()
    }

    override fun onFragmentDestroyed(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentDestroyed(fm, f)
        mFragmentWrapperMap[f]?.onDestroy()
    }

    override fun onFragmentDetached(
        fm: FragmentManager,
        f: Fragment
    ) {
        super.onFragmentDetached(fm, f)
        mFragmentWrapperMap[f]?.let {
            it.onDetach()
            mFragmentWrapperMap.remove(f)
        }
    }
}