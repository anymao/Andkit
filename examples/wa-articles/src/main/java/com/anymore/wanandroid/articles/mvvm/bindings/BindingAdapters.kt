package com.anymore.wanandroid.articles.mvvm.bindings

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.anymore.wanandroid.repository.base.Status


object BindingAdapters {

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageBitmap")
    @JvmStatic
    fun loadImage(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }


    @BindingAdapter("isSelected")
    @JvmStatic
    fun setImageViewSelected(imageView: ImageView, selected: Boolean) {
        imageView.isSelected = selected
    }


    @BindingAdapter("isLoading")
    @JvmStatic
    fun showLoading(view: View, status: Status) {
        view.visibility = if (status == Status.DOING) View.VISIBLE else View.GONE
    }


}
