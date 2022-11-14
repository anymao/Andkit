@file:Suppress("UNCHECKED_CAST")

package com.anymore.andkit.common.ktx

import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import timber.log.Timber
import java.io.Serializable

/**
 * 兼容一下DRouter的场景，DRouter中：native://loan/main?disableRedirect=true&checkLogin=true
 * checkLogin和disableRedirect是以String形式存储在Extra中的，这个时候getBoolean是无法取到的，
 * 可以采用下面这个兼容获取boolean方式来处理
 * 特殊的，如果我们之后定义1也为true，则在else前面添加分支即可
 */
fun Bundle.getBooleanCompatible(key: String, defaultValue: Boolean = false): Boolean {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is Boolean -> {
            value
        }
        is String -> {
            value.contentEquals("true", ignoreCase = true)
        }
        else -> {
            defaultValue
        }
    }
}

/**
 * 兼容获取String
 */
fun Bundle.getStringCompatible(key: String, defaultValue: String? = null): String? {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is String -> {
            value
        }
        else -> {
            value.toString()
        }
    }
}

/**
 * 兼容获取Int
 */
fun Bundle.getIntCompatible(key: String, defaultValue: Int = 0): Int {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is Int -> {
            value
        }
        is Number -> {
            value.toInt()
        }
        is String -> {
            tryOr(defaultValue) {
                value.toInt()
            }
        }
        else -> {
            defaultValue
        }
    }
}

/**
 * 兼容获取Long
 */
fun Bundle.getLongCompatible(key: String, defaultValue: Long = 0): Long {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is Long -> {
            value
        }
        is Number -> {
            value.toLong()
        }
        is String -> {
            tryOr(defaultValue) {
                value.toLong()
            }
        }
        else -> {
            defaultValue
        }
    }
}

/**
 * 兼容获取Float
 */
fun Bundle.getFloatCompatible(key: String, defaultValue: Float = 0f): Float {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is Float -> {
            value
        }
        is Number -> {
            value.toFloat()
        }
        is String -> {
            tryOr(defaultValue) {
                value.toFloat()
            }
        }
        else -> {
            defaultValue
        }
    }
}

/**
 * 兼容获取Double
 */
fun Bundle.getDoubleCompatible(key: String, defaultValue: Double = 0.0): Double {
    val value = get(key) ?: return defaultValue
    return when (value) {
        is Double -> {
            value
        }
        is Number -> {
            value.toDouble()
        }
        is String -> {
            tryOr(defaultValue) {
                value.toDouble()
            }
        }
        else -> {
            defaultValue
        }
    }
}

/**
 * putAny并非能够put任意类型的value，仍然是基于Bundle支持的类型的封装
 */
fun Bundle.putAny(key: String, value: Any?) {

    if (value == null) {
        putString(key, null)
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        when (value) {
            is Size -> {
                putSize(key, value)
                return
            }
            is SizeF -> {
                putSizeF(key, value)
                return
            }
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        when (value) {
            is Binder -> {
                putBinder(key, value)
                return
            }
        }
    }

    when (value) {
        is String -> putString(key, value)
        is CharSequence -> putCharSequence(key, value)
        is CharArray -> putCharArray(key, value)
        is Byte -> putByte(key, value)
        is Boolean -> putBoolean(key, value)
        is Short -> putShort(key, value)
        is Char -> putChar(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is ByteArray -> putByteArray(key, value)
        is BooleanArray -> putBooleanArray(key, value)
        is ShortArray -> putShortArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is ArrayList<*> -> putParcelableArrayList(
            key,
            value as? ArrayList<out Parcelable>
        )
        is SparseArray<*> -> putSparseParcelableArray(
            key,
            value as? SparseArray<out Parcelable>
        )
        is Array<*> -> putParcelableArray(key, value as? Array<out Parcelable>)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> {
            tryOrNothing {
                val json = value.toJson()
                putString(key, json)
                Timber.w("暂不支持${value}的类型:${value.className},[$key:$value]将被以兼容方式[json]存入，请使用getEntryCompatible取数据")
            }
        }
    }
}

/**
 * 将[map]中的数据以key-value的形式塞入[Bundle]中
 * 注意:map支持的类型有限，超出支持类型的数据将不会被塞入到[Bundle]中
 */
fun Bundle.accept(map: Map<String, Any?>) = apply {
    map.forEach { putAny(it.key, it.value) }
}

/**
 * 兼容获取Entry类型对象
 * 如果Entry是通过putSerializable之类的方式直接塞进来的，则直接获取
 * 对于一些可能以json格式塞进来的，则可这样获取
 */
fun <T : Any> Bundle.getEntryCompatible(key: String, clazz: Class<T>): T? {
    val value = get(key) ?: return null
    if (clazz.isInstance(value)) {
        return clazz.cast(value)
    }
    if (value is String) {
        return value.toEntry(clazz)
    }
    Timber.w("key=${key}所对应的value类型为:${value.className},与目标类型不符合")
    return null
}

inline fun <reified T : Any> Bundle.getEntryCompatible(key: String) =
    getEntryCompatible(key, T::class.java)

/**
 * Bundle转Map
 */
fun Bundle.toMap(): HashMap<String, Any?> {
    val result = hashMapOf<String, Any?>()
    keySet().forEach {
        result[it] = get(it)
    }
    return result
}