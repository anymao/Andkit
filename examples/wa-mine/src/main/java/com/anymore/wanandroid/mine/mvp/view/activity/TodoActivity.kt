package com.anymore.wanandroid.mine.mvp.view.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpActivity
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.mine.entry.Todo
import com.anymore.wanandroid.mine.event.TodoChangedEvent
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mine.mvp.contract.TodoContract
import com.anymore.wanandroid.mine.mvp.view.widget.DatePickerDialog
import com.anymore.wanandroid.route.MINE_TODO
import com.anymore.wanandroid.route.NEED_LOGIN_FLAG
import com.anymore.wanandroid.route.TODO_DATA
import com.anymore.wanandroid.route.TODO_OPERATION
import kotlinx.android.synthetic.main.wm_activity_todo.*
import kotlinx.android.synthetic.main.wm_public_include_submit.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anymore on 2020/2/2.
 */
@Route(path = MINE_TODO, extras = NEED_LOGIN_FLAG)
class TodoActivity : BaseMvpActivity<TodoContract.ITodoPresenter>(), TodoContract.ITodoView,
    DatePickerDialog.OnButtonClickListener {


    companion object {

        const val TODO_DATE_FORMAT = "yyyy-MM-dd"
        const val TYPE_CREATE = 0
        const val TYPE_BROWSE = 1
        const val TYPE_EDIT = 2
    }

    @Autowired(name = TODO_OPERATION, required = true, desc = "操作类型")
    @JvmField
    var mType = TYPE_CREATE

    @Autowired(name = TODO_DATA, required = true, desc = "todo数据")
    @JvmField
    var mTodo: Todo? = null

    var todoType = 0

    private val sdf by lazy { SimpleDateFormat(TODO_DATE_FORMAT, Locale.getDefault()) }
    private val datePickDialog by lazy {
        DatePickerDialog(this).also {
            it.setOnButtonClickListener(this)
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        ARouter.getInstance().inject(this)
        return R.layout.wm_activity_todo
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupToolbar(toolbar)
        rgTodoType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbOnlyOne -> todoType = 0
                R.id.rbStudy -> todoType = 1
                R.id.rbWork -> todoType = 2
                R.id.rbLife -> todoType = 3
            }
        }
        tvDate.setOnClickListener {
            datePickDialog.show()
        }
        if (mTodo != null) {
            fleContent.setValue(mTodo!!.content)
            fivTitle.valueText = mTodo!!.title
            tvDate.text = mTodo!!.completeDateStr
        } else {
            mType = TYPE_CREATE
        }
        showSubmitButtonFunction(mType)
        btnSubmit.setOnClickListener {
            if (mType == TYPE_CREATE || mType == TYPE_EDIT) {
                submitTodo()
            } else {
                mType = TYPE_EDIT
                showSubmitButtonFunction(mType)
            }
        }
        btnDelete.setOnClickListener {
            deleteTodo()
        }
    }

    override fun success(type: Int, message: String) {
        showSuccess(message)
        EventBus.getDefault().post(TodoChangedEvent(message))
        finish()
    }

    override fun onSelected(date: Date) {
        tvDate.text = sdf.format(date)
    }

    override fun onClear() {
        tvDate.text = ""
    }

    override fun onCancel() {

    }

    private fun showSubmitButtonFunction(type: Int) {
        val title = when (type) {
            TYPE_CREATE -> R.string.wm_create_new_todo
            TYPE_BROWSE -> R.string.wm_todo_title
            TYPE_EDIT -> R.string.wm_todo_edit
            else -> 0
        }
        tvTitle.setText(title)
        btnDelete.visibility = if (type == TYPE_CREATE) {
            View.GONE
        } else {
            View.VISIBLE
        }
        when (type) {
            TYPE_BROWSE -> btnSubmit.text = "编辑"
            else -> btnSubmit.text = "提交"
        }
    }

    private fun submitTodo() {
        val title = fivTitle.valueText
        val content = fleContent.valueText
        val date = tvDate.text?.toString() ?: ""
        if (mTodo != null) {
            mPresenter.updateTodo(mTodo!!.id, title, content, date, todoType)
        } else {
            mPresenter.saveTodo(title, content, date, todoType)
        }
    }

    private fun deleteTodo() {
        mTodo?.let {
            mPresenter.deleteTodo(it.id)
        }
    }

}