package com.anymore.wanandroid.articles.loader

import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.anymore.wanandroid.articles.entry.Banner as BannerData

class BannerLoader(private val mBanner: Banner) : OnBannerListener {


    private var mData: List<BannerData>? = null
    private val mImages by lazy { ArrayList<String>() }
    private val mTitles by lazy { ArrayList<String>() }
    var mListener: ((banner: BannerData) -> Unit)? = null

    fun initBanner(listener: ((banner: BannerData) -> Unit)? = null) {
        //设置banner样式(显示圆形指示器)
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置指示器位置（指示器居右）
        mBanner.setIndicatorGravity(BannerConfig.CENTER)
        //设置图片加载器
        mBanner.setImageLoader(GlideImageLoader())
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000)
        mListener = listener
    }

    fun loadData(data: List<BannerData>) {
        mData = data
        mImages.clear()
        mTitles.clear()
        mData?.let {
            for (item in it) {
                if (item.isVisible == 1) {
                    mImages.add(item.imagePath)
                    mTitles.add(item.title)
                }
            }
            load()
        }
    }


    private fun load() {
        mBanner.setImages(mImages)
        mBanner.setBannerTitles(mTitles)
        mBanner.setOnBannerListener(this)
        mBanner.start()
    }

    fun startAutoPlay() {
        mBanner.startAutoPlay()
    }

    fun stopAutoPlay() {
        mBanner.stopAutoPlay()
    }

    override fun OnBannerClick(position: Int) {
        mListener?.run {
            if (position <= mData?.size ?: 0) {
                invoke(mData?.get(position)!!)
            }
        }
    }
}