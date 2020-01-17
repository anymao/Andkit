package com.anymore.andkit.lifecycle

import android.app.Activity
import android.text.TextUtils
import timber.log.Timber
import java.util.*

/**
 * activity 管理栈
 */
object ActivityStackManager {

    private val mActivityStack by lazy { LinkedList<Activity>() }

    internal fun add(activity: Activity){
        Timber.d("add activity:$activity")
        mActivityStack.add(activity)
    }

    internal fun remove(activity: Activity){
        Timber.d("remove activity:$activity")
        mActivityStack.remove(activity)
    }

    fun getTopActivity():Activity?{
        if (mActivityStack.isNotEmpty()){
            return mActivityStack.last!!
        }
        return null
    }

    fun finishActivity(activity: Activity){
        if (!activity.isFinishing){
            activity.finish()
        }
    }

    fun finishActivity(activityName:String){
        for (activity in mActivityStack){
            if (TextUtils.equals(activityName,activity.javaClass.name)){
                finishActivity(activity)
            }
        }
    }

    fun finishUntil(activity: Activity){
        val  iterator = mActivityStack.iterator()
        while (iterator.hasNext()){
            val nextActivity = iterator.next()
            if (nextActivity != activity){
                finishActivity(nextActivity)
            }else{
                continue
            }
        }
    }

    fun finishUntil(activityName: String){
        val  iterator = mActivityStack.iterator()
        while (iterator.hasNext()){
            val nextActivity = iterator.next()
            if (!TextUtils.equals(nextActivity.javaClass.name,activityName)){
                finishActivity(nextActivity)
            }else{
                continue
            }
        }
    }

    fun finishAll(){
        for (activity in mActivityStack){
            finishActivity(activity)
        }
    }
}