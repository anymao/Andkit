package com.anymore.wanandroid.browse.view

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.common.ext.dp2px
import com.anymore.wanandroid.route.BROWSE_URL
import com.anymore.wanandroid.route.URL_VALUE
import timber.log.Timber

/**
 * Created by anymore on 2020/8/30.
 */
class BrowseMainActivity : AppCompatActivity(), IActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16))
        }
        setContentView(list)
        list.dividerDrawable = ColorDrawable(Color.GRAY)
        list.dividerPadding = dp2px(1)
        val btnBaidu = TextView(this).apply {
            text = "Baidu"
            setOnClickListener {
                ARouter.getInstance().build(BROWSE_URL)
                    .withString(URL_VALUE, "https://www.baidu.com").navigation()
            }
            gravity = Gravity.CENTER_HORIZONTAL
        }
        val navigateByScheme = TextView(this).apply {
            text = "Navigate By Scheme"
            setOnClickListener {
                val target = Uri.Builder()
                    .scheme("wanandroid")
                    .authority("browse")
                    .path("navigate")
                    .appendQueryParameter("url","https://github.com/anymao")
                    .build()
                Timber.d(target.toString())
                startActivity(Intent(ACTION_VIEW,target))
            }
            gravity = Gravity.CENTER_HORIZONTAL
        }
        list.addView(btnBaidu, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(48)))
        list.addView(navigateByScheme, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(48)))
    }
}