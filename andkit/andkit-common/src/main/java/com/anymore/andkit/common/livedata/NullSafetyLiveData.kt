package com.anymore.andkit.common.livedata

import androidx.lifecycle.MediatorLiveData

/**
 * Created by anymore on 2022/3/29.
 */

/**
 * https://proandroiddev.com/improving-livedata-nullability-in-kotlin-45751a2bafb7
 *
 * 为了解决LiveData在kotlin中空安全问题所引入的新的LiveData
 * 原始LiveData存在的问题：
 *<pre>
 *     val l = MutableLiveData<String>()
 *     l.observeForever {
 *          doSth(it)//这里编译器提示it 类型为String!但是其实初始it是可能为null的，如果我们直接将it当作不为空处理，实际运行时候会有空指针
 *     }
 *</pre>
 *<pre>
 *     val dl1 = NullSafetyLiveData<String>("")
 *     dl1.observeForever {
 *          it != null//it的类型为String!,我们不必判断it为空的情况
 *     }
 *     val dl2 = NullSafetyLiveData<String?>(null)
 *     dl2.observeForever {
 *          it != null//it的类型为String?,我们使用it的时候必须考虑为null的情况
 *     }
 *</pre>
 */
class NullSafetyLiveData<T> private constructor() : MediatorLiveData<T>() {

    companion object {
        fun <T> empty(): NullSafetyLiveData<T?> = NullSafetyLiveData()
    }

    /**
     * 隐藏了无参构造器，如果想声明一个可以为空的LiveData
     * 1.val l1 = NullSafetyLiveData<String?>(null)
     * 或者
     * 2.val l2 = NullSafetyLiveData.empty<String>()
     *
     * 以上声明的l1 或者 l2 在onChanged回调中 it 的类型均会提示为String?,因此你必须考虑it为null的情况
     * 如果想声明一个不为空的LiveData
     * val l3 = NullSafetyLiveData<String>("")
     * 声明的l3在onChanged回调中 it 的类型会提示为String!，因此你无须考虑it为null的情况
     * 因为l3范型声明为String，所以l3.value=null这样的操作会提示为非法的，正常一般情况下，l3不会被set null
     */
    constructor(initValue: T) : this() {
        value = initValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(): T = super.getValue() as T

}