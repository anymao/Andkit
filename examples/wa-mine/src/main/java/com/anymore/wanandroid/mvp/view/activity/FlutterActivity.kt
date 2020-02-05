package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.lifecycle.activity.IActivity
import com.anymore.wanandroid.route.MINE_FLUTTER
import io.flutter.facade.Flutter

/**
 * Created by anymore on 2020/2/5.
 */
@Route(path = MINE_FLUTTER)
class FlutterActivity : AppCompatActivity(), IActivity {


    @Autowired
    @JvmField
    var initialRoute:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        val flutter = Flutter.createView(this, lifecycle, initialRoute)
        addContentView(
            flutter,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
}