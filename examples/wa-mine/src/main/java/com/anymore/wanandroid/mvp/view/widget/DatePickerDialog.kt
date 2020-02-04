package com.anymore.wanandroid.mvp.view.widget

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.anymore.wanandroid.mine.R
import kotlinx.android.synthetic.main.wm_dialog_date_picker.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anymore on 2020/2/2.
 */
class DatePickerDialog(context: Context) : AlertDialog(context) {

    private var mListener: OnButtonClickListener? = null
    private var mDate  = Date()
    private val sdf by lazy { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()) }
    companion object{
        const val DATE_FORMAT = "yyyy-MM-dd"
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        mListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wm_dialog_date_picker)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            mDate = sdf.parse("$year-$month-$dayOfMonth")?:Date()
        }
        tvEnsure.setOnClickListener {
            mListener?.onSelected(mDate)
            dismiss()
        }
        tvCancel.setOnClickListener {
            mListener?.onCancel()
            dismiss()
        }
        tvClear.setOnClickListener {
            mListener?.onClear()
            dismiss()
        }

    }


    interface OnButtonClickListener {
        fun onSelected(date: Date)
        fun onClear()
        fun onCancel()
    }
}