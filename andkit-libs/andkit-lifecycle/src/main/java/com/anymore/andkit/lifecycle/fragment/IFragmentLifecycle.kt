package com.anymore.andkit.lifecycle.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by liuyuanmao on 2019/2/23.
 */
interface IFragmentLifecycle {
    /**
     * 代理 [Fragment.onAttach]
     *
     * @param context Context
     */
    fun onAttach(context: Context)

    /**
     * 代理 [Fragment.onCreate]
     *
     * @param savedInstanceState 数据恢复
     */
    fun onCreate(savedInstanceState: Bundle?) {}

    /**
     * 代理 [Fragment.onViewCreated]
     *
     * @param view               View
     * @param savedInstanceState 数据恢复
     */
    fun onCreateView(view: View, savedInstanceState: Bundle?) {}

    /**
     * 代理 [Fragment.onActivityCreated]
     *
     * @param savedInstanceState 数据恢复
     */
    fun onActivityCreate(savedInstanceState: Bundle?)

    /**
     * 代理 [Fragment.onStart]
     */
    fun onStart() {}

    /**
     * 代理 [Fragment.onResume]
     */
    fun onResume() {
        Timber.i("IFragmentLifecycle onResume")
    }

    /**
     * 代理 [Fragment.onPause]
     */
    fun onPause() {
        Timber.i("IFragmentLifecycle onPause")
    }

    /**
     * 代理 [Fragment.onStop]
     */
    fun onStop() {}

    /**
     * 代理 [onSaveInstanceState]
     *
     * @param outState 数据保存
     */
    fun onSaveInstanceState(outState: Bundle) {}

    /**
     * 代理 [Fragment.onDestroyView]
     */
    fun onDestroyView() {}

    /**
     * 代理 [Fragment.onDestroy]
     */
    fun onDestroy() {}

    /**
     * 代理 [Fragment.onDetach]
     */
    fun onDetach()

    /**
     * Fragment 是否添加到 Activity
     *
     * @return true if the fragment is currently added to its activity.
     */
    fun isAdded(): Boolean
}