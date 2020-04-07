package com.anymore.wanandroid.mvp.view.activity

import android.os.Bundle
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
import com.anymore.wanandroid.route.MINE_TODO
import com.anymore.wanandroid.route.NEED_LOGIN_FLAG
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

    }

    override fun success(type: Int, message: String) {
        showSuccess(message)
        EventBus.getDefault().post(TodoChangedEvent(message))
        finish()
    }

    override fun onSelected(date: Date) {
    }

    override fun onClear() {
    }

    override fun onCancel() {

    }

}