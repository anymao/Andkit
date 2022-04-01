package com.anymore.wanandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anymore.andkit.core.base.BaseActivity
import com.anymore.andkit.core.ktx.launchWithLoading
import com.anymore.wanandroid.repository.rpc.Repository
import com.anymore.wanandroid.repository.rpc.service.WanAndroidService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

//    @Inject
//    lateinit var retrofit: Retrofit

    private val service by lazy { Repository.test() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchWithLoading {
            val vo = withContext(Dispatchers.IO){
                service.getHomeBanners()
            }
            Timber.d(vo.toString())
        }
    }

    override fun showLoading(text: String?) {
        Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "dismiss", Toast.LENGTH_SHORT).show()
    }
}