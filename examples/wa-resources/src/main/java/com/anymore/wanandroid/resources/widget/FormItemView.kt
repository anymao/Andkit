package com.anymore.wanandroid.resources.widget

import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.method.DigitsKeyListener
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.core.view.ViewCompat
import com.anymore.wanandroid.resources.R
import com.anymore.wanandroid.resources.exts.dp2px


/**
 * Created by anymore on 2020/2/12.
 */
class FormItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr) {
    /**
     * 左边key的TextView
     */
    private lateinit var mTvKey: TextView
    /**
     * 右边输入框的EditText框
     */
    private lateinit var mEtValue: ClearEditText
    /**
     * 是否是必须字段，如果是则在key后面加上红星号
     */
    private var mIsNecessaryField: Boolean = false
    /**
     * 是否可编辑
     */
    private var mIsValueEditable: Boolean = false

    /**
     * 设置key的值
     *
     * @param text
     */
    var keyText: String
        get() = mTvKey.text?.toString() ?: ""
        set(text) {
            mTvKey.text = getDecoratedKeyText(text)
        }

    /**
     * 设置value的值
     *
     * @param text
     */
    var valueText: String
        get() = mEtValue.text?.toString() ?: ""
        set(text) = mEtValue.setText(text)

    val valueEditText: EditText?
        get() = mEtValue

    init {
        initView(context, attrs, defStyleAttr)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //        if (heightMode == MeasureSpec.AT_MOST){
        //            int paddingTop = getPaddingTop();
        //            setPadding(0,paddingTop,0,paddingTop);
        //        }
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FormItemView, defStyleAttr, 0)
        val keyText = ta.getString(R.styleable.FormItemView_key)
        val keyTextSize =
            ta.getDimensionPixelSize(R.styleable.FormItemView_keyTextSize, DEFAULT_KEY_TEXT_SIZE)
        val keyTextColor =
            ta.getColor(R.styleable.FormItemView_keyTextColor, DEFAULT_KEY_TEXT_COLOR)
        val keyTextGravity = ta.getInt(R.styleable.FormItemView_keyTextGravity, Gravity.RIGHT)
        val valueText = ta.getString(R.styleable.FormItemView_value)
        val valueTextSize = ta.getDimensionPixelSize(
            R.styleable.FormItemView_valueTextSize,
            DEFAULT_VALUE_TEXT_SIZE
        )
        val valueTextColor =
            ta.getColor(R.styleable.FormItemView_valueTextColor, DEFAULT_VALUE_TEXT_COLOR)
        val valueTextHint = ta.getString(R.styleable.FormItemView_valueTextHint)
        mIsValueEditable = ta.getBoolean(R.styleable.FormItemView_valueEditable, false)
        val valueTextMinLines = ta.getInt(R.styleable.FormItemView_valueTextMinLines, 1)
        val valueInputType = ta.getInt(
            R.styleable.FormItemView_valueInputType,
            InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
        )
        val formWeight = ta.getFloat(R.styleable.FormItemView_formWeight, -1.0f)
        val valueMultipleEnable = ta.getBoolean(R.styleable.FormItemView_valueMultipleEnable, true)
        val valueTextGravity = ta.getInt(R.styleable.FormItemView_valueTextGravity, Gravity.START)
        val valueDigits = ta.getString(R.styleable.FormItemView_valueDigits)
        val valueMaxLength = ta.getInt(R.styleable.FormItemView_valueMaxLength, Integer.MAX_VALUE)
        mIsNecessaryField = ta.getBoolean(R.styleable.FormItemView_isNecessary, false)
        ta.recycle()
        mTvKey = TextView(getContext())
        mEtValue = ClearEditText(getContext(), null)
        this.keyText = keyText ?: ""
        mTvKey.setPadding(0, 0, 0, 0)
        mTvKey.setTextColor(keyTextColor)
        mTvKey.setTextSize(TypedValue.COMPLEX_UNIT_SP, keyTextSize.toFloat())

        mEtValue.setPadding(context.dp2px(8), 0, 0, 0)
        mEtValue.setText(valueText)
        mEtValue.hint = valueTextHint
        mEtValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, valueTextSize.toFloat())
        mEtValue.setTextColor(valueTextColor)
        mEtValue.minLines = valueTextMinLines
        mEtValue.inputType = valueInputType
        setValueMaxLength(valueMaxLength)
        if (!valueDigits.isNullOrEmpty()) {
            mEtValue.keyListener = DigitsKeyListener.getInstance(valueDigits)
        }

        if (!valueMultipleEnable) {
            mEtValue.maxLines = 1
        }
        //单行居中，多行则从上往下布局（设置上下padding 14dp）
        if (!valueMultipleEnable) {
            mTvKey.gravity = keyTextGravity or Gravity.CENTER_VERTICAL
            mEtValue.gravity = Gravity.CENTER_VERTICAL or valueTextGravity
        } else {
            mTvKey.gravity = keyTextGravity or Gravity.TOP
            mEtValue.gravity = Gravity.TOP or valueTextGravity
        }
        ViewCompat.setBackground(mEtValue, null)
        mEtValue.isFocusable = mIsValueEditable
        mEtValue.isEnabled = mIsValueEditable
        setValueEditable(mIsValueEditable)
        val keyLayoutParams: LayoutParams
        val valueLayoutParams: LayoutParams
        if (formWeight > 0 && formWeight < 1) {
            val keyWeight = (formWeight * 100).toInt()
            val valueWeight = 100 - keyWeight
            keyLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            keyLayoutParams.weight = keyWeight.toFloat()
            valueLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            valueLayoutParams.weight = valueWeight.toFloat()
        } else {
            keyLayoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
            )
            valueLayoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        addView(mTvKey, keyLayoutParams)
        addView(mEtValue, valueLayoutParams)
    }

    /**
     * 重写onInterceptTouchEvent
     * 当设置FormItemView不可编辑的时候，本意上是相当于EditText等价于TextView的效果
     * 但是由于EditText自身的原因，EditText会消耗点击事件，当点击在EditText上面的时候没有反应
     * 在这里当FormItemView不可编辑的时候，触摸事件不向下传递
     *
     * @param ev
     * @return
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (mIsValueEditable) {
            super.onInterceptTouchEvent(ev)
        } else {
            true
        }
    }

    /**
     * 设置Value是否可编辑
     *
     * @param editable
     */
    fun setValueEditable(editable: Boolean) {
        if (mIsValueEditable != editable) {
            mIsValueEditable = editable
            mEtValue.isFocusable = mIsValueEditable
            mEtValue.isEnabled = mIsValueEditable
            mEtValue.isFocusableInTouchMode = mIsValueEditable
            if (!mIsValueEditable) {
                mEtValue.keyListener = null
            }

        }
    }

    /**
     * 设置Key和Value的值
     *
     * @param key
     * @param value
     */
    fun setKeyAndValueText(key: String, value: String) {
        keyText = key
        valueText = value
    }

    /**
     * 设置输入值的inputType
     *
     * @param type
     */
    fun setValueInputType(type: Int) {
        if (mIsValueEditable) {
            mEtValue.inputType = type
        }
    }

    fun setValueHint(hint: String) {
        mEtValue.hint = hint
    }

    fun setValueMaxLength(@IntRange(from = 0) length: Int) {
        mEtValue.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
    }

    private fun getDecoratedKeyText(src: String?): CharSequence? {
        val isShowAsterisk = mIsNecessaryField && mIsValueEditable
        return if (isShowAsterisk) {
            val red = ForegroundColorSpan(Color.parseColor("#FF0000"))
            val ss = SpannableString(src!! + "*")
            ss.setSpan(red, src.length, src.length + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            ss
        } else {
            src
        }
    }

    companion object {

        val DEFAULT_KEY_TEXT_COLOR = Color.parseColor("#999999")
        const val DEFAULT_KEY_TEXT_SIZE = 14
        val DEFAULT_VALUE_TEXT_COLOR = Color.parseColor("#333333")
        const val DEFAULT_VALUE_TEXT_SIZE = 14
        const val DEFAULT_VERTICAL_PADDING = 14
    }

}
