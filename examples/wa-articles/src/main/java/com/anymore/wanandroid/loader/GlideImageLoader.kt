package com.anymore.wanandroid.loader

import android.content.Context
import android.widget.ImageView
import com.anymore.wanandroid.repository.glide.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.youth.banner.loader.ImageLoader

class GlideImageLoader:ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null && imageView != null){
            GlideApp.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }
}