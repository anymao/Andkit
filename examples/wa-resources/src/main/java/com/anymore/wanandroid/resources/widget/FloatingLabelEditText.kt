package com.anymore.wanandroid.resources.widget

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.anymore.wanandroid.resources.R
import com.anymore.wanandroid.resources.exts.dp2px
import java.util.*

/**
 * Created by anymore on 2020/2/16.
 */
class FloatingLabelEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : RelativeLayout(context, attrs, defStyleAttr), TextWatcher, View.OnClickListener,
    View.OnFocusChangeListener {
    private val mInputMethodManager: InputMethodManager?


    private lateinit var mTvLabel: TextView
    private lateinit var mTvRemainingWords: TextView
    private lateinit var mEtValue: EditText

    //attrs
    private var mLabel: String? = null
    private var mLabelTextColor: Int = 0
    private var mLabelTextSize: Int = 0
    private var mLabelFloatTextSize: Int = 0
    private var mLabelFloatTextColor: Int = 0
    private var mWords: Int = 0
    private var mWordsTextSize: Int = 0
    private var mWordsTextColor: Int = 0
    private var mValue: String? = null
    private var mValueTextColor: Int = 0
    private var mValueTextSize: Int = 0
    private var mValueHint: String? = null
    private var mValueEditable: Boolean = false
    /**
     * 是否是必须字段，如果是则在key后面加上红星号
     */
    private var mIsNecessaryField: Boolean = false

    private var mValueWordsCount: Int = 0

    val labelText: String
        get() = mTvLabel.text.toString()

    val valueText: String
        get() = mEtValue.text.toString()

    val valueEditText: EditText
        get() = mEtValue

    init {
        mInputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        initView(context, attrs, defStyleAttr)
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        placeViews()
        val ta = context.obtainStyledAttributes(
            attrs,
            R.styleable.FloatingLabelEditText,
            defStyleAttr,
            -1
        )
        mLabel = ta.getString(R.styleable.FloatingLabelEditText_label)
        mLabelTextColor =
            ta.getColor(R.styleable.FloatingLabelEditText_labelTextColor, DEFAULT_LABEL_TEXT_COLOR)
        mLabelTextSize = ta.getDimensionPixelSize(
            R.styleable.FloatingLabelEditText_labelTextSize,
            DEFAULT_LABEL_TEXT_SIZE
        )
        mLabelFloatTextColor = ta.getColor(
            R.styleable.FloatingLabelEditText_labelFloatTextColor,
            DEFAULT_FLOAT_LABEL_TEXT_COLOR
        )
        mLabelFloatTextSize = ta.getDimensionPixelSize(
            R.styleable.FloatingLabelEditText_labelFloatTextSize,
            DEFAULT_FLOAT_LABEL_TEXT_SIZE
        )
        mWords = ta.getInt(R.styleable.FloatingLabelEditText_words, DEFAULT_WORDS)
        mWordsTextColor =
            ta.getColor(R.styleable.FloatingLabelEditText_wordsTextColor, DEFAULT_WORDS_TEXT_COLOR)
        mWordsTextSize = ta.getDimensionPixelSize(
            R.styleable.FloatingLabelEditText_wordsTextSize,
            DEFAULT_WORDS_TEXT_SIZE
        )
        mValue = ta.getString(R.styleable.FloatingLabelEditText_value)
        mValueTextColor =
            ta.getColor(R.styleable.FloatingLabelEditText_valueTextColor, DEFAULT_VALUE_TEXT_COLOR)
        mValueTextSize = ta.getDimensionPixelSize(
            R.styleable.FloatingLabelEditText_valueTextSize,
            DEFAULT_VALUE_TEXT_SIZE
        )
        mValueHint = ta.getString(R.styleable.FloatingLabelEditText_valueTextHint)
        mValueEditable = ta.getBoolean(R.styleable.FloatingLabelEditText_valueEditable, true)
        mValueWordsCount = if (mValue != null) mValue!!.length else 0
        mIsNecessaryField = ta.getBoolean(R.styleable.FloatingLabelEditText_isNecessary, false)
        ta.recycle()
        setAttrs()
    }

    private fun placeViews() {
        setOnClickListener(this)
        mTvLabel = TextView(context)
        mTvLabel.id = R.id.wa_tv_fle_label
        mTvRemainingWords = TextView(context)
        mTvRemainingWords.id = R.id.wa_tv_fle_remain_words
        mEtValue = EditText(context)
        mEtValue.id = R.id.wa_et_fle_value
        ViewCompat.setBackground(mEtValue, null)
        val params2 = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params2.addRule(ALIGN_PARENT_RIGHT)
        params2.addRule(ALIGN_BASELINE, R.id.wa_tv_fle_label)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params2.addRule(ALIGN_PARENT_END)
        }

        val params1 = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        params1.addRule(ALIGN_PARENT_LEFT)
        params1.addRule(ALIGN_PARENT_TOP)
        params1.addRule(LEFT_OF, R.id.wa_tv_fle_remain_words)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params1.addRule(ALIGN_PARENT_START)
        }

        val params3 = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        params3.addRule(BELOW, R.id.wa_tv_fle_label)
        params3.addRule(ALIGN_LEFT, R.id.wa_tv_fle_label)
        params3.addRule(ALIGN_RIGHT, R.id.wa_tv_fle_remain_words)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params3.addRule(ALIGN_START, R.id.wa_tv_fle_label)
            params3.addRule(ALIGN_END, R.id.wa_tv_fle_remain_words)
        }


        mEtValue.addTextChangedListener(this)
        mEtValue.setPadding(0, context.dp2px(8), 0, 0)

        addView(mTvRemainingWords, params2)
        addView(mTvLabel, params1)
        addView(mEtValue, params3)

        mEtValue.onFocusChangeListener = this
    }

    private fun setAttrs() {
        //EditText限制输入字数
        mEtValue.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(mWords))
        mEtValue.setTextColor(mValueTextColor)
        mEtValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, mValueTextSize.toFloat())
        setValue(mValue, mValueHint, mValueEditable)
        setLabel(mLabel)
        mTvRemainingWords.setTextColor(mWordsTextColor)
        mTvRemainingWords.setTextSize(TypedValue.COMPLEX_UNIT_SP, mWordsTextSize.toFloat())
        setRemainingWordsCount()
    }

    @JvmOverloads
    fun setValue(value: String?, valueHint: String? = "", valueEditable: Boolean = mValueEditable) {
        mValueHint = valueHint
        mValue = value
        mEtValue.setText(mValue)
        mEtValue.hint = mValueHint
        if (TextUtils.isEmpty(mEtValue.text)) {
            mEtValue.visibility = View.GONE
            mTvLabel.setTextColor(mLabelTextColor)
            mTvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLabelTextSize.toFloat())
        } else {
            mEtValue.visibility = View.VISIBLE
            setValueEditable(valueEditable)
            mTvLabel.setTextColor(mLabelFloatTextColor)
            mTvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLabelFloatTextSize.toFloat())
        }
    }


    fun setValueEditable(editable: Boolean) {
        mTvRemainingWords.visibility = if (editable) View.VISIBLE else View.INVISIBLE
        mValueEditable = editable
        setLabel(mLabel)
        mEtValue.isFocusable = mValueEditable
        mEtValue.isEnabled = mValueEditable
        mEtValue.isFocusableInTouchMode = mValueEditable
        isClickable = mValueEditable
    }

    /**
     * 设置是否是必要字段，但是是否显示小红点是必要字段且可编辑
     * @param isNecessaryField
     */
    fun setIsNecessaryField(isNecessaryField: Boolean) {
        this.mIsNecessaryField = isNecessaryField
        setLabel(mLabel)
    }

    fun setLabel(label: String?) {
        mLabel = label
        mTvLabel.text = getDecoratedLabelText(mLabel)
    }

    private fun setRemainingWordsCount() {
        mTvRemainingWords.text =
            String.format(Locale.getDefault(), "%d字", mWords - mValueWordsCount)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        mValueWordsCount = s.length
        setRemainingWordsCount()
    }

    override fun onClick(v: View) {
        if (mValueEditable) {
            mEtValue.visibility = View.VISIBLE
            mEtValue.requestFocus()
            val text = mEtValue.text
            val length = text?.length ?: 0
            mEtValue.setSelection(length)
            mInputMethodManager?.showSoftInput(mEtValue, 0)
            mTvLabel.setTextColor(mLabelFloatTextColor)
            mTvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLabelFloatTextSize.toFloat())
        }
    }


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            val value = mEtValue.text.toString()
            setValue(value)
        }
    }

    private fun getDecoratedLabelText(src: String?): CharSequence? {
        val isShowAsterisk = mIsNecessaryField && mValueEditable
        if (isShowAsterisk) {
            val red = ForegroundColorSpan(Color.parseColor("#FF0000"))
            val ss = SpannableString(src!! + "*")
            ss.setSpan(red, src.length, src.length + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            return ss
        } else {
            return src
        }
    }

    companion object {
        private val DEFAULT_LABEL_TEXT_COLOR = Color.parseColor("#333333")
        private const val DEFAULT_LABEL_TEXT_SIZE = 14
        private val DEFAULT_FLOAT_LABEL_TEXT_COLOR = Color.parseColor("#999999")
        private const val DEFAULT_FLOAT_LABEL_TEXT_SIZE = 12
        private val DEFAULT_WORDS_TEXT_COLOR = Color.parseColor("#999999")
        private const val DEFAULT_WORDS_TEXT_SIZE = 12
        private val DEFAULT_VALUE_TEXT_COLOR = Color.parseColor("#333333")
        private const val DEFAULT_VALUE_TEXT_SIZE = 14
        private const val DEFAULT_WORDS = 500
    }
}
