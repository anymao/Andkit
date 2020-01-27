package com.anymore.wanandroid.loader

import android.content.Context
import android.widget.ImageView
import com.anymore.wanandroid.common.glide.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.youth.banner.loader.ImageLoader
import timber.log.Timber

class GlideImageLoader:ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null && imageView != null){
            Timber.d("path:$path")
            GlideApp.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }
}