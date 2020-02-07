package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
import android.text.InputType
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.anymore.andkit.mvp.BaseMvpActivity
import com.anymore.wanandroid.common.ext.setupToolbar
import com.anymore.wanandroid.entry.Todo
import com.anymore.wanandroid.event.TodoChangedEvent
import com.anymore.wanandroid.mine.R
import com.anymore.wanandroid.mvp.contract.TodoContract
import com.anymore.wanandroid.mvp.view.widget.DatePickerDialog
import com.anymore.wanandroid.route.NEED_LOGIN_FLAG
import com.anymore.wanandroid.route.MINE_TODO

import kotlinx.android.synthetic.main.wm_activity_todo.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anymore on 2020/2/2.
 */
@Route(path = MINE_TODO,extras = NEED_LOGIN_FLAG)
class TodoActivity : BaseMvpActivity<TodoContract.ITodoPresenter>(), TodoContract.ITodoView,
    DatePickerDialog.OnButtonClickListener {


    companion object {

        const val TODO_DATE_FORMAT = "yyyy-MM-dd"
        const val TYPE_CREATE = 0
        const val TYPE_BROWSE = 1
        const val TYPE_EDIT = 2
    }

    @Autowired
    @JvmField
    var mType = TYPE_CREATE

    @Autowired
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
        initTodo()
        rgTodoType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbOnlyOne -> todoType = 0
                R.id.rbStudy -> todoType = 1
                R.id.rbWork -> todoType = 2
                R.id.rbLife -> todoType = 3
            }
        }
        tvTodoDate.setOnClickListener {
            datePickDialog.show()
        }
        btnSave.setOnClickListener {
            if (mType == TYPE_BROWSE){
                freezeLayout(false)
                btnSave.setText(R.string.wm_text_save)
                mType = TYPE_EDIT
                tvTitle.setText(R.string.wm_todo_edit)
            }else{
                if (mTodo != null){
                    val id = mTodo!!.id
                    val title = etTodoTitle.text.toString()
                    val content = etTodoContent.text.toString()
                    val date = tvTodoDate.text.toString()
                    mPresenter.updateTodo(id,title,content,date,todoType)
                }else{
                    saveTodo()
                }
            }
        }
        btnDelete.setOnClickListener {
            if (mTodo != null){
                mPresenter.deleteTodo(mTodo!!.id)
            }
        }
    }

    private fun initTodo() {
        title = ""
        if (mType == TYPE_CREATE || mTodo == null) {
            mType = TYPE_CREATE
            tvTitle.setText(R.string.wm_create_new_todo)
            tvTodoDate.text = sdf.format(Date())
            freezeLayout(false)
            btnSave.setText(R.string.wm_text_save)
            btnDelete.isVisible = false
        } else if (mType == TYPE_BROWSE) {
            tvTitle.setText(R.string.wm_todo_title)
            freezeLayout(true)
            btnSave.setText(R.string.wm_text_edit)
            btnDelete.isVisible = true
        } else if (mType == TYPE_EDIT) {
            tvTitle.setText(R.string.wm_todo_edit)
            freezeLayout(false)
            btnSave.setText(R.string.wm_text_save)
            btnDelete.isVisible = true
        }
        if (mTodo != null) {
            etTodoTitle.setText(mTodo!!.title)
            etTodoContent.setText(mTodo!!.content)
            tvTodoDate.text = mTodo!!.completeDateStr
        }

    }

    private fun saveTodo() {
        val title = etTodoTitle.text.toString()
        val content = etTodoContent.text.toString()
        val date = tvTodoDate.text.toString()
        mPresenter.saveTodo(title,content,date,todoType)
    }

    override fun success(type: Int, message: String) {
        showSuccess(message)
        EventBus.getDefault().post(TodoChangedEvent(message))
        finish()
    }

    override fun onSelected(date: Date) {
        tvTodoDate.text = sdf.format(date)
    }

    override fun onClear() {
        tvTodoDate.text = ""
    }

    override fun onCancel() {

    }

    private fun freezeLayout(freeze:Boolean){
        if (freeze){
            etTodoTitle.inputType = InputType.TYPE_NULL
            etTodoContent.inputType = InputType.TYPE_NULL
            rgTodoType.isEnabled = false
            tvTodoDate.isEnabled = false
        }else{
            etTodoTitle.inputType = InputType.TYPE_CLASS_TEXT
            etTodoContent.inputType = InputType.TYPE_CLASS_TEXT
            rgTodoType.isEnabled = true
            tvTodoDate.isEnabled = true
        }
    }

}