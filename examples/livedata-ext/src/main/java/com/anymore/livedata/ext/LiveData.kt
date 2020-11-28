package com.anymore.livedata.ext

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by anymore on 2020/11/28.
 */

@MainThread
fun <T> MutableLiveData<T>.setDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        setValue(value)
    }
}

fun <T> MutableLiveData<T>.postDiffValue(value: T) {
    val old = getValue()
    if (old != value) {
        postValue(value)
    }
}


@MainThread
fun <T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> {
    val result = MutableLiveData<R>()
    observeForever { x -> result.value = mapper(x) }
    return result
}

@MainThread
fun <X, Y> LiveData<X>.switchMap(
    switchMapFunction: (X) -> MutableLiveData<Y>
): LiveData<Y> {
    val result = MutableLiveData<Y>()
    observeForever(object : Observer<X> {
        var mSource: MutableLiveData<Y>? = null
        val ob: Observer<in Y> = Observer { result.value = it }
        val ob2: Observer<Y> = Observer {
            if (it != mSource?.value) {
                mSource?.value = it
            }
        }

        override fun onChanged(x: X) {
            val newLiveData = switchMapFunction(x)
            if (mSource === newLiveData) {
                return
            }
            if (mSource != null) {
                mSource!!.removeObserver(ob)
                result.removeObserver(ob2)
            }
            mSource = newLiveData
            if (mSource != null) {
                result.value = mSource!!.value
                mSource!!.observeForever(ob)
                result.observeForever(ob2)
            }
        }
    })
    return result
}

@MainThread
fun <T> LiveData<T>.distinct(): LiveData<T> {
    val result = MutableLiveData<T>()
    var newest: T? = null
    observeForever { data: T ->
        if (data != newest) {
            newest = data
            result.value = data
        }
    }
    return result
}

@MainThread
fun <T> LiveData<T>.skip(skipCount: Int = 1): LiveData<T> {
    val result = MutableLiveData<T>()
    var count = 0
    observeForever { v ->
        if (++count > skipCount) {
            result.value = v
        }
    }
    return result
}

@MainThread
fun <T1, T2, R> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    combiner: (T1?, T2?) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    var latestData1: T1? = liveData1.value
    var latestData2: T2? = liveData2.value
    liveData1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2)
    }
    liveData2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2)
    }
    return result
}

@MainThread
fun <T1, T2, T3, R> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    liveData3: LiveData<T3>,
    combiner: (T1?, T2?, T3?) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    var latestData1: T1? = liveData1.value
    var latestData2: T2? = liveData2.value
    var latestData3: T3? = liveData3.value
    liveData1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2, latestData3)
    }
    liveData2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2, latestData3)
    }
    liveData3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value = combiner(latestData1, latestData2, data3)
    }

    return result
}

@MainThread
fun <T1, T2, T3, T4, R> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    liveData3: LiveData<T3>,
    liveData4: LiveData<T4>,
    combiner: (T1?, T2?, T3?, T4?) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    var latestData1: T1? = liveData1.value
    var latestData2: T2? = liveData2.value
    var latestData3: T3? = liveData3.value
    var latestData4: T4? = liveData4.value
    liveData1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2, latestData3, latestData4)
    }
    liveData2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2, latestData3, latestData4)
    }
    liveData3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value = combiner(latestData1, latestData2, data3, latestData4)
    }
    liveData4.observeForever { data4: T4 ->
        latestData4 = data4
        result.value = combiner(latestData1, latestData2, latestData3, data4)
    }
    return result
}

@MainThread
fun <T1, T2, T3, T4, T5, R> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    liveData3: LiveData<T3>,
    liveData4: LiveData<T4>,
    liveData5: LiveData<T5>,
    combiner: (T1?, T2?, T3?, T4?, T5?) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    var latestData1: T1? = liveData1.value
    var latestData2: T2? = liveData2.value
    var latestData3: T3? = liveData3.value
    var latestData4: T4? = liveData4.value
    var latestData5: T5? = liveData5.value
    liveData1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value = combiner(data1, latestData2, latestData3, latestData4, latestData5)
    }
    liveData2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value = combiner(latestData1, data2, latestData3, latestData4, latestData5)
    }
    liveData3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value = combiner(latestData1, latestData2, data3, latestData4, latestData5)
    }
    liveData4.observeForever { data4: T4 ->
        latestData4 = data4
        result.value = combiner(latestData1, latestData2, latestData3, data4, latestData5)
    }
    liveData5.observeForever { data5: T5 ->
        latestData5 = data5
        result.value = combiner(latestData1, latestData2, latestData3, latestData4, latestData5)
    }
    return result
}

@MainThread
fun <T1, T2, T3, T4, T5, T6, R> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    liveData3: LiveData<T3>,
    liveData4: LiveData<T4>,
    liveData5: LiveData<T5>,
    liveData6: LiveData<T6>,
    combiner: (T1?, T2?, T3?, T4?, T5?, T6?) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    var latestData1: T1? = liveData1.value
    var latestData2: T2? = liveData2.value
    var latestData3: T3? = liveData3.value
    var latestData4: T4? = liveData4.value
    var latestData5: T5? = liveData5.value
    var latestData6: T6? = liveData6.value
    liveData1.observeForever { data1: T1 ->
        latestData1 = data1
        result.value =
            combiner(data1, latestData2, latestData3, latestData4, latestData5, latestData6)
    }
    liveData2.observeForever { data2: T2 ->
        latestData2 = data2
        result.value =
            combiner(latestData1, data2, latestData3, latestData4, latestData5, latestData6)
    }
    liveData3.observeForever { data3: T3 ->
        latestData3 = data3
        result.value =
            combiner(latestData1, latestData2, data3, latestData4, latestData5, latestData6)
    }
    liveData4.observeForever { data4: T4 ->
        latestData4 = data4
        result.value =
            combiner(latestData1, latestData2, latestData3, data4, latestData5, latestData6)
    }
    liveData5.observeForever { data5: T5 ->
        latestData5 = data5
        result.value =
            combiner(latestData1, latestData2, latestData3, latestData4, data5, latestData6)
    }
    liveData6.observeForever { data6: T6 ->
        latestData6 = data6
        result.value =
            combiner(latestData1, latestData2, latestData3, latestData4, latestData5, data6)
    }
    return result
}


@MainThread
fun <T1, T2> combine(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>
): LiveData<Pair<T1?, T2?>> {
    return combine(liveData1, liveData2) { d1, d2 -> d1 to d2 }
}

@MainThread
fun <T, R> combine(
    vararg liveDataList: LiveData<out T>,
    combiner: (List<T>) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    if (!liveDataList.any { it.value == null }) {
        result.value = combiner(liveDataList.map { it.value!! })
    }
    liveDataList.forEach { liveData ->
        liveData.observeForever {
            if (liveDataList.any { it.value == null })
                return@observeForever
            result.value = combiner(liveDataList.map { it.value!! })
        }
    }
    return result
}

@MainThread
fun <T1, T2, R> merge(
    l1: LiveData<T1>,
    m1: (T1) -> R,
    l2: LiveData<T2>,
    m2: (T2) -> R
): LiveData<R> {
    val result = MutableLiveData<R>()
    l1.observeForever { d1 -> result.value = m1(d1) }
    l2.observeForever { d2 -> result.value = m2(d2) }
    return result
}

@MainThread
fun <T, R> merge(
    vararg pairs: Pair<LiveData<T>, (T) -> R>
): LiveData<R> {
    val result = MutableLiveData<R>()
    pairs.forEach { pair ->
        val (l, m) = pair
        l.observeForever { d -> result.value = m(d) }
    }
    return result
}

@MainThread
fun <T> merge(
    vararg ls: LiveData<T>
): LiveData<T> {
    val result = MutableLiveData<T>()
    ls.forEach { l ->
        l.observeForever { d -> result.value = d }
    }
    return result
}

@MainThread
fun <T, R> LiveData<List<T>>.mapList(m: (T) -> R): LiveData<List<R>> {
    return map { list -> list.map(m) }
}

@MainThread
fun <T, R> LiveData<out List<LiveData<out T>>>.mapWithCombine(m: (List<T>) -> R): LiveData<R> {
    val result = MutableLiveData<R>()
    val ob = Observer<R> { result.value = it }
    var prevL: LiveData<R>? = null
    map { liveDataList ->
        prevL?.apply {
            removeObserver(ob)
        }
        prevL = combine(liveDataList = (liveDataList.toTypedArray()), combiner = m).apply {
            observeForever(ob)
        }
    }
    return result
}