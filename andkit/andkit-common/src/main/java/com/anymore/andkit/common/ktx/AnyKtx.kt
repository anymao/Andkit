package com.anymore.andkit.common.ktx


inline val <T : Any> T.className: String get() = javaClass.name

inline val <T : Any> T.uniqueTag: String get() = "${className}@${hashCode()}"