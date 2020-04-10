package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.mvp.view.widget.FlutterLoadingView
import com.anymore.wanandroid.route.MINE_FLUTTER
import io.flutter.facade.Flutter

/**
 * Created by anymore on 2020/2/5.
 */
@Route(path = MINE_FLUTTER)
class FlutterActivity : AppCompatActivity(), IActivity {


    @Autowired
    @JvmField
    var initialRoute: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        val flutterContainer = findViewById<FrameLayout>(android.R.id.content)
        val flutter = Flutter.createView(this, lifecycle, initialRoute)
        val flutterLoading = FlutterLoadingView(this)
        flutterContainer.addView(
            flutter,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        flutterContainer.addView(
            flutterLoading, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        flutter.addFirstFrameListener {
            flutterLoading.removeSelf()
        }
    }
}