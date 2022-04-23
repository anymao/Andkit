package com.anymore.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.anymore.andkit.common.ktx.click
import com.anymore.andkit.common.loader.LoadCallback
import com.anymore.andkit.common.loader.Loader

/**
 * Created by anymore on 2022/4/10.
 */
class ContentWrapper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewAnimator(context, attrs), LoadCallback, Loader {

    private val spaceChild =
        Child(
            _index = State.SPACE.index,
            layoutId = R.layout.widget_cw_space_view,
            showRetry = false
        )
    val loadingChild =
        Child(
            _index = State.LOADING.index,
            layoutId = R.layout.widget_cw_loading_view,
            indicatorId = R.raw.widget_content_wrapper_loading,
            showRetry = false
        )
    val emptyChild = Child(
        _index = State.EMPTY.index,
        layoutId = R.layout.widget_cw_empty_view,
        indicatorId = R.raw.widget_content_wrapper_empty,
        title = "没有找到结果",
        showRetry = false
    )

    val failedChild = Child(
        _index = State.FAILED.index,
        layoutId = R.layout.widget_cw_failed_view,
        indicatorId = R.raw.widget_content_wrapper_failed,
        title = "加载失败",
        retryText = "点击重试"
    )

    private val children = listOf(spaceChild, loadingChild, emptyChild, failedChild)

    var emptyClickable = true
        set(value) {
            field = value
            emptyChild.clickable = value
        }

    var state: State = State.LOADING
        set(value) {
            field = value
            display(children.getOrNull(value.index)?.index)
        }

    override var loader: (() -> Unit)? = null
        set(value) {
            field = value
            listOf(emptyChild, failedChild).forEach {
                it.click {
                    state = State.LOADING
                    loader?.invoke()
                }
            }
        }

    var enableCover = true
        set(value) {
            field = value
            children.forEach { it.enable = value }
            if (!value) {
                state = State.SUCCESS
            }
        }

    var enableEmpty = true
        set(value) {
            field = value
            emptyChild.enable = value
        }


    init {
        var firstState = State.SUCCESS
        context.obtainStyledAttributes(attrs, R.styleable.ContentWrapper).let {
            spaceChild.layoutId =
                it.getResourceId(R.styleable.ContentWrapper_cwSpaceLayout, spaceChild.layoutId)
            loadingChild.layoutId = it.getResourceId(
                R.styleable.ContentWrapper_cwLoadingLayout,
                loadingChild.layoutId
            )
            loadingChild.indicatorId = it.getResourceId(
                R.styleable.ContentWrapper_loadingIndicatorRes,
                loadingChild.indicatorId
            )
            loadingChild.title =
                it.getString(R.styleable.ContentWrapper_loadingTitle) ?: loadingChild.title
            loadingChild.subTitle =
                it.getString(R.styleable.ContentWrapper_loadingSubTitle) ?: loadingChild.subTitle
            emptyChild.layoutId =
                it.getResourceId(R.styleable.ContentWrapper_cwEmptyLayout, emptyChild.layoutId)
            emptyChild.title =
                it.getString(R.styleable.ContentWrapper_emptyTitle) ?: emptyChild.title
            emptyChild.subTitle =
                it.getString(R.styleable.ContentWrapper_emptySubTitle) ?: emptyChild.subTitle
            emptyChild.indicatorId = it.getResourceId(
                R.styleable.ContentWrapper_emptyIndicatorRes,
                emptyChild.indicatorId
            )
            emptyChild.retryText =
                it.getString(R.styleable.ContentWrapper_emptyRetryText) ?: emptyChild.retryText
            emptyChild.showRetry =
                it.getBoolean(R.styleable.ContentWrapper_showEmptyRetry, emptyChild.showRetry)
            failedChild.layoutId = it.getResourceId(
                R.styleable.ContentWrapper_cwFailedLayout,
                failedChild.layoutId
            )
            failedChild.title =
                it.getString(R.styleable.ContentWrapper_failedTitle) ?: failedChild.title
            failedChild.subTitle =
                it.getString(R.styleable.ContentWrapper_failedSubTitle) ?: failedChild.subTitle
            failedChild.indicatorId = it.getResourceId(
                R.styleable.ContentWrapper_failedIndicatorRes,
                failedChild.indicatorId
            )
            failedChild.retryText =
                it.getString(R.styleable.ContentWrapper_failedRetryText) ?: failedChild.retryText
            failedChild.showRetry =
                it.getBoolean(R.styleable.ContentWrapper_showFailedRetry, failedChild.showRetry)

            val firstStateIndex = it.getInt(R.styleable.ContentWrapper_state, firstState.index)
            firstState = State.parse(firstStateIndex)
            it.recycle()

        }
        children.forEachIndexed { index, child ->
            child.view = inflate(context, child.layoutId, null)
            addView(child.view, index)
        }
        state = firstState
    }

    override fun load() {
        state = State.LOADING
        loader?.invoke()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        state = state
    }


    @JvmOverloads
    fun setLoadingIndicatorRes(
        @IdRes indicatorView: Int = R.id.cw_iv_indicator,
        indicatorId: Int
    ) {
        if (indicatorView != R.id.cw_iv_indicator) {
            loadingChild.setCustomIndicator(indicatorView, indicatorId)
        } else {
            loadingChild.indicatorId = indicatorId
        }
    }

    @JvmOverloads
    fun setLoadingTitle(@IdRes titleView: Int = R.id.cw_tv_title, title: CharSequence?) {
        if (titleView != R.id.cw_tv_title) {
            loadingChild.setCustomTitle(titleView, title)
        } else {
            loadingChild.title = title
        }
    }

    @JvmOverloads
    fun setLoadingSubTitle(
        @IdRes subTitleView: Int = R.id.cw_tv_sub_title,
        subTitle: CharSequence?
    ) {
        if (subTitleView != R.id.cw_tv_sub_title) {
            loadingChild.setCustomSubTitle(subTitleView, subTitle)
        } else {
            loadingChild.subTitle = subTitle
        }
    }


    @JvmOverloads
    fun setEmptyIndicatorRes(@IdRes indicatorView: Int = R.id.cw_iv_indicator, indicatorId: Int) {
        if (indicatorView != R.id.cw_iv_indicator) {
            emptyChild.setCustomIndicator(indicatorView, indicatorId)
        } else {
            emptyChild.indicatorId = indicatorId
        }
    }

    /**
     * 设置空页面时候的主标题
     * [titleView]默认的标题控件id是[R.id.cw_tv_title]如果你的自定义页面标题控件不是这个，那就请指定
     * 其他几个方法都是类似作用
     */
    @JvmOverloads
    fun setEmptyTitle(@IdRes titleView: Int = R.id.cw_tv_title, title: CharSequence?) {
        if (titleView != R.id.cw_tv_title) {
            emptyChild.setCustomTitle(titleView, title)
        } else {
            emptyChild.title = title
        }
    }

    @JvmOverloads
    fun setEmptySubTitle(
        @IdRes subTitleView: Int = R.id.cw_tv_sub_title,
        subTitle: CharSequence?
    ) {
        if (subTitleView != R.id.cw_tv_sub_title) {
            emptyChild.setCustomSubTitle(subTitleView, subTitle)
        } else {
            emptyChild.subTitle = subTitle
        }
    }

    @JvmOverloads
    fun setEmptyRetryText(@IdRes retryView: Int = R.id.cw_tv_retry, retryText: CharSequence?) {
        if (retryView != R.id.cw_tv_retry) {
            emptyChild.setCustomRetryText(retryView, retryText)
        } else {
            emptyChild.retryText = retryText
        }
    }

    @JvmOverloads
    fun setFailedIndicatorRes(@IdRes indicatorView: Int = R.id.cw_iv_indicator, indicatorId: Int) {
        if (indicatorView != R.id.cw_iv_indicator) {
            failedChild.setCustomIndicator(indicatorView, indicatorId)
        } else {
            failedChild.indicatorId = indicatorId
        }
    }

    @JvmOverloads
    fun setFailedTitle(@IdRes titleView: Int = R.id.cw_tv_title, title: CharSequence?) {
        if (titleView != R.id.cw_tv_title) {
            failedChild.setCustomTitle(titleView, title)
        } else {
            failedChild.title = title
        }
    }

    @JvmOverloads
    fun setFailedSubTitle(
        @IdRes subTitleView: Int = R.id.cw_tv_sub_title,
        subTitle: CharSequence?
    ) {
        if (subTitleView != R.id.cw_tv_sub_title) {
            failedChild.setCustomSubTitle(subTitleView, subTitle)
        } else {
            failedChild.subTitle = subTitle
        }
    }

    @JvmOverloads
    fun setFailedRetryText(@IdRes retryView: Int = R.id.cw_tv_retry, retryText: CharSequence?) {
        if (retryView != R.id.cw_tv_retry) {
            failedChild.setCustomRetryText(retryView, retryText)
        } else {
            failedChild.retryText = retryText
        }
    }


    fun display(childIndex: Int?) {
        displayedChild =
            childIndex ?: if (childCount > children.size) childCount - 1 else State.SPACE.index
    }

    fun setOnClickListener(
        id: Int,
        listener: () -> Unit
    ) {
        children.forEach {
            it.click(id, listener)
        }
    }

    /**
     * 如果view为null则没有对应页面，显示SUCCESS内容,
     * setLoader等操作要在此操作之后调用，否则没有点击事件
     */
    fun replaceChild(state: State = State.EMPTY, view: View?) {
        children.getOrNull(state.index)?.let { child ->
            if (view == null) {
                child.enable = false
            } else {
                child.enable = true
                child.index?.let { index ->
                    removeViewAt(index)
                    addView(view, index)
                    child.view = view
                }
            }
        }
    }


    class Child internal constructor(
        private val _index: Int,
        var layoutId: Int,
        var enable: Boolean = true,
        indicatorId: Int = 0,
        title: CharSequence? = null,
        subTitle: CharSequence? = null,
        retryText: CharSequence? = null,
        showRetry: Boolean = true
    ) {

        val index: Int?
            get() = if (enable) _index else null

        var indicatorView: ImageView? = null
            private set

        var titleView: TextView? = null
            private set

        var subTitleView: TextView? = null
            private set

        var retryView: TextView? = null
            private set


        var indicatorId: Int = indicatorId
            set(value) {
                field = value
                val ivIndicator = indicatorView
                if (ivIndicator is LottieAnimationView) {
                    ivIndicator.setAnimation(field)
                } else {
                    ivIndicator?.setImageResource(field)
                }
            }

        var title: CharSequence? = title
            set(value) {
                field = value
                titleView?.text = value
            }

        var subTitle: CharSequence? = subTitle
            set(value) {
                field = value
                subTitleView?.text = value
            }
        var retryText: CharSequence? = retryText
            set(value) {
                field = value
                retryView?.text = value
            }

        var showRetry: Boolean = showRetry
            set(value) {
                field = value
                retryView?.isVisible = value
            }


        fun setCustomTitle(@IdRes titleView: Int, title: CharSequence?) {
            getView<TextView>(titleView)?.text = title
        }

        fun setCustomSubTitle(@IdRes subTitleView: Int, subTitle: CharSequence?) {
            getView<TextView>(subTitleView)?.text = subTitle
        }

        fun setCustomRetryText(@IdRes retryView: Int, retryText: CharSequence?) {
            getView<TextView>(retryView)?.text = retryText
        }

        fun setCustomIndicator(@IdRes indicatorView: Int, indicatorId: Int) {
            val view = getView<ImageView>(indicatorView)
            if (view is LottieAnimationView) {
                view.setAnimation(indicatorId)
            } else {
                view?.setImageResource(indicatorId)
            }
        }


        var view: View? = null
            set(value) {
                field = value
                indicatorView = value?.findViewById(R.id.cw_iv_indicator)
                indicatorId = indicatorId
                titleView = value?.findViewById(R.id.cw_tv_title)
                title = title
                subTitleView = value?.findViewById(R.id.cw_tv_sub_title)
                subTitle = subTitle
                retryView = value?.findViewById(R.id.cw_tv_retry)
                retryText = retryText
                showRetry = showRetry
            }

        var clickable = true
            set(value) {
                field = value
                view?.isClickable = value
            }

        fun <V : View> getView(@IdRes id: Int): V? = view?.findViewById(id)

        fun click(id: Int = -1, onClick: () -> Unit) {
            if (id > 0) {
                view?.findViewById<View>(id)
                    ?.click { onClick() }
            } else {
                view?.click { onClick() }
            }
        }
    }

    enum class State(val index: Int) {
        SPACE(0),//页面加载前的空白页面
        LOADING(1),//页面加载中
        EMPTY(2),//加载完了但是没有数据
        FAILED(3),//加载失败
        SUCCESS(4);//加载成功;

        companion object {
            fun parse(index: Int): State =
                values().firstOrNull { it.index == index } ?: SUCCESS
        }
    }

    override fun onPrepare() {
        state = State.LOADING
    }

    override fun onSuccess() {
        state = State.SUCCESS
    }

    override fun onFailed(throwable: Throwable?): Boolean {
        state = State.FAILED
        return true
    }

    override fun onFinal() {

    }

}