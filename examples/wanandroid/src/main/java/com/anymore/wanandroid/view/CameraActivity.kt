package com.anymore.wanandroid.view

import android.Manifest
import android.os.Bundle
import com.anymore.andkit.lifecycle.checkPermissions
import com.anymore.wanandroid.common.ext.toast
import com.anymore.andkit.mvvm.BindingActivity
import com.anymore.camera.CameraxFragment
import com.anymore.wanandroid.R
import com.anymore.wanandroid.databinding.ActicityCameraBinding

/**
 * Created by anymore on 2020/11/15.
 */
class CameraActivity : BindingActivity<ActicityCameraBinding>() {

    private lateinit var mFragment: CameraxFragment
    override fun initView(savedInstanceState: Bundle?) = R.layout.acticity_camera

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        checkPermissions(Manifest.permission.CAMERA, refusedEvent = {
            toast("必须授予相机权限")
            true
        }) {
            mFragment = CameraxFragment().apply {
                onCatchBitmapListener = {
                    toast("catch a bitmap:@${it.hashCode()}")

                }
            }
            mFragmentManager.beginTransaction().add(R.id.flContainer, mFragment).commitAllowingStateLoss()
        }
    }
}