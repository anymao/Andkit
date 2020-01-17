package com.anymore.wanandroid.common.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 利用kotlin属性委托的特性实现的SharedPreference读写
 * Created by liuyuanmao on 2019/6/18.
 */
class SharedPreferenceDelegate<T : Any>(
    private val name: String,
    private val default: T,
    private val spName: String = "default"
) : ReadWriteProperty<Any, T> {
    private val mSharedPreferenceHelper by lazy { SharedPreferenceHelper(spName) }
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return mSharedPreferenceHelper.get(name, default)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        mSharedPreferenceHelper.put(name, value)
    }

}