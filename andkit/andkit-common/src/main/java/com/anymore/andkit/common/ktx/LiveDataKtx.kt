package com.anymore.andkit.common.ktx

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anymore.andkit.common.livedata.NullSafetyLiveData

/**
 * LiveData相关扩展，类似RxJava
 */
/**
 * 同setValue但是会忽略和已有值一样的数据
 * setDiffValue: 1 1 2 2 3 4
 * result:       1   2   3 4
 */
@MainThread
fun <T> MutableLiveData<T>.setDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        setValue(value)
    }
}

/**
 * @see setDiffValue
 */
fun <T> MutableLiveData<T>.postDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        postValue(value)
    }
}

@MainThread
fun <T, R> LiveData<T>.map(mapper: (T?) -> R): NullSafetyLiveData<R> {
    val result = NullSafetyLiveData(mapper(value))
    observeForever { x -> result.value = mapper(x) }
    return result
}


/**
 * 过滤掉重复的数据项
 * source: 1 2 2 3 3 4 5
 * result: 1 2   3   4 5
 */
@MainThread
fun <T> LiveData<T>.distinct(): NullSafetyLiveData<T?> {
    val result = NullSafetyLiveData(value)
    var newest: T? = null
    observeForever { data: T ->
        if (data != newest) {
            newest = data
            result.value = data
        }
    }
    return result
}


/**
 * 跳过发射的前N项数据
 * source:1 2 3 4 5 skipCount = 2
 * result:    3 4 5
 */
@MainThread
fun <T> LiveData<T>.skip(skipCount: Int = 1): NullSafetyLiveData<T?> {
    val result = NullSafetyLiveData.empty<T>()
    var count = 0
    observeForever { v ->
        if (++count > skipCount) {
            result.value = v
        }
    }
    return result
}

/**
 * 将两个LiveData结合
 * example:
 *
 * combiner:"${v1}${v2}"//简单将结果拼接成字符串
 * l1:     1  2  3  4  5
 * l2:     a     b     c
 * result:1a  2a 3b 4b 5c
 * 需要注意的是：result必须要被激活（result.observe 或者 result.observeForever）才能完成对l1和l2的合并
 */
@MainThread
fun <T1, T2, R> combine(
    l1: LiveData<T1>,
    l2: LiveData<T2>,
    combiner: (T1?, T2?) -> R
): NullSafetyLiveData<R> {
    var latestData1: T1? = l1.value
    var latestData2: T2? = l2.value
    val result = NullSafetyLiveData(combiner(latestData1, latestData2))
    l1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2)
    }
    l2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2)
    }
    return result
}

@MainThread
fun <T1, T2, R> combine(
    l1: NullSafetyLiveData<T1>,
    l2: NullSafetyLiveData<T2>,
    combiner: (T1, T2) -> R
): NullSafetyLiveData<R> {
    var latestData1 = l1.value
    var latestData2 = l2.value
    val result = NullSafetyLiveData(combiner(latestData1, latestData2))
    l1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2)
    }
    l2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2)
    }
    return result
}

@MainThread
fun <T1, T2, T3, R> combine(
    l1: LiveData<T1>,
    l2: LiveData<T2>,
    l3: LiveData<T3>,
    combiner: (T1?, T2?, T3?) -> R
): NullSafetyLiveData<R> {
    var latestData1: T1? = l1.value
    var latestData2: T2? = l2.value
    var latestData3: T3? = l3.value
    val result = NullSafetyLiveData(combiner(latestData1, latestData2, latestData3))
    l1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2, latestData3)
    }
    l2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2, latestData3)
    }
    l3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value = combiner(latestData1, latestData2, data3)
    }
    return result
}

@MainThread
fun <T1, T2, T3, T4, R> combine(
    l1: LiveData<T1>,
    l2: LiveData<T2>,
    l3: LiveData<T3>,
    l4: LiveData<T4>,
    combiner: (T1?, T2?, T3?, T4?) -> R
): NullSafetyLiveData<R> {
    var latestData1: T1? = l1.value
    var latestData2: T2? = l2.value
    var latestData3: T3? = l3.value
    var latestData4: T4? = l4.value
    val result = NullSafetyLiveData(combiner(latestData1, latestData2, latestData3, latestData4))
    l1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2, latestData3, latestData4)
    }
    l2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2, latestData3, latestData4)
    }
    l3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value = combiner(latestData1, latestData2, data3, latestData4)
    }
    l4.observeForever { data4: T4 ->
        latestData4 = data4
        result.value = combiner(latestData1, latestData2, latestData3, data4)
    }
    return result
}


@MainThread
fun <T1, T2> combine(
    l1: LiveData<T1>,
    l2: LiveData<T2>
): NullSafetyLiveData<Pair<T1?, T2?>> {
    return combine(l1, l2) { d1, d2 -> d1 to d2 }
}

/**
 * 将两个不同的数据流转换成同一个数据流
 * m1:Int->String
 * m2:Char->String
 * l1    :1      3    5
 * l2    :   a     b
 * result:1  a   3 b  5
 */
@MainThread
fun <T1, T2, R> merge(
    l1: LiveData<T1>,
    m1: (T1?) -> R,
    l2: LiveData<T2>,
    m2: (T2?) -> R
): NullSafetyLiveData<R?> {
    val result = NullSafetyLiveData.empty<R>()
    l1.observeForever { d1 -> result.value = m1(d1) }
    l2.observeForever { d2 -> result.value = m2(d2) }
    return result
}

@MainThread
fun <T1, T2, T3, R> merge(
    l1: LiveData<T1>,
    m1: (T1) -> R,
    l2: LiveData<T2>,
    m2: (T2) -> R,
    l3: LiveData<T3>,
    m3: (T3) -> R
): NullSafetyLiveData<R?> {
    val result = NullSafetyLiveData.empty<R>()
    l1.observeForever { d1 -> result.value = m1(d1) }
    l2.observeForever { d2 -> result.value = m2(d2) }
    l3.observeForever { d3 -> result.value = m3(d3) }
    return result
}

@MainThread
fun <T, R> merge(
    vararg pairs: Pair<LiveData<T>, (T) -> R>
): NullSafetyLiveData<R?> {
    val result = NullSafetyLiveData.empty<R>()
    pairs.forEach { pair ->
        val (l, m) = pair
        l.observeForever { d -> result.value = m(d) }
    }
    return result
}

@MainThread
fun <T> merge(
    vararg ls: LiveData<T>
): NullSafetyLiveData<T?> {
    val result = NullSafetyLiveData.empty<T>()
    ls.forEach { l ->
        result.addSource(l) {
            result.value = it
        }
    }
    return result
}


@MainThread
fun MutableLiveData<Boolean>.toggle() {
    value = value?.not()
}

