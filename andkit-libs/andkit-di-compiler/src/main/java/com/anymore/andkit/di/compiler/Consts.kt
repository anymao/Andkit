package com.anymore.andkit.di.compiler

import com.squareup.kotlinpoet.ClassName


/**
 * Created by anymore on 2021/1/31.
 */
internal const val SUPPORT_OPTION_MODULE_NAME = "ANDKIT_MODULE_NAME"
internal const val ANNOTATION_PACKAGE_NAME = "com.anymore.andkit.di"
internal const val SUPPORT_ANNOTATION_RETROFIT_SERVICE = "$ANNOTATION_PACKAGE_NAME.RetrofitService"
internal const val SUPPORT_ANNOTATION_ROOM_DAO = "$ANNOTATION_PACKAGE_NAME.RoomDao"
internal const val SUPPORT_ANNOTATION_DATA_BUNDLE = "$ANNOTATION_PACKAGE_NAME.DataBundle"
internal const val EXTRA = "andkitExtra"

// System interface
internal const val ACTIVITY = "android.app.Activity"
internal const val FRAGMENT = "android.app.Fragment"
internal const val FRAGMENT_V4 = "android.support.v4.app.Fragment"
internal const val FRAGMENT_X = "androidx.fragment.app.Fragment"

internal const val SERVICE = "android.app.Service"
internal const val PARCELABLE = "android.os.Parcelable"

// Java type
private const val LANG = "java.lang"
internal const val BYTE = "$LANG.Byte"
internal const val SHORT = "$LANG.Short"
internal const val INTEGER = "$LANG.Integer"
internal const val LONG = "$LANG.Long"
internal const val FLOAT = "$LANG.Float"
internal const val DOUBLE = "$LANG.Double"
internal const val BOOLEAN = "$LANG.Boolean"
internal const val CHAR = "$LANG.Character"
internal const val STRING = "$LANG.String"
internal const val SERIALIZABLE = "java.io.Serializable"

//Hilt
val DAGGER_MODULE_ANNOTATION = ClassName("dagger", "Module")
val DAGGER_PROVIDES_ANNOTATION = ClassName("dagger", "Provides")
val NULLABLE_ANNOTATION = ClassName("androidx.annotation", "Nullable")
val DAGGER_BIND_ANNOTATION = ClassName("dagger", "Binds")
val NAMED_ANNOTATION = ClassName("javax.inject", "Named")
val SINGLETON_ANNOTATION = ClassName("javax.inject", "Singleton")
val HILT_INSTALL_IN_ANNOTATION = ClassName("dagger.hilt", "InstallIn")
val HILT_ACTIVITY_CONTEXT_ANNOTATION =
    ClassName("dagger.hilt.android.qualifiers", "ActivityContext")
val ACTIVITY_SCOPED_ANNOTATION = ClassName("dagger.hilt.android.scopes", "ActivityScoped")
val FRAGMENT_SCOPED_ANNOTATION = ClassName("dagger.hilt.android.scopes", "FragmentScoped")
val SINGLETON_COMPONENT_CLASS =
    ClassName("dagger.hilt.components", "SingletonComponent")

@Deprecated("use SINGLETON_COMPONENT_CLASS")
val APP_COMPONENT_CLASS =
    ClassName("dagger.hilt.android.components", "ApplicationComponent")
val ACTIVITY_COMPONENT_CLASS =
    ClassName("dagger.hilt.android.components", "ActivityComponent")
val FRAGMENT_COMPONENT_CLASS =
    ClassName("dagger.hilt.android.components", "FragmentComponent")

val CONTEXT_CLASS = ClassName("android.content", "Context")
val FRAGMENT_CLASS = ClassName("androidx.fragment.app", "Fragment")
val ACTIVITY_CLASS = ClassName("android.app", "Activity")

val REPOSITORY_CONFIG_CLASS = ClassName("com.anymore.andkit.repository.configs", "RepositoryConfig")