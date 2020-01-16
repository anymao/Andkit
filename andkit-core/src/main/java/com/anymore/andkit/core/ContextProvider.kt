package com.anymore.andkit.core

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import kotlin.properties.Delegates

/**
 * 一个全局的[Context]提供器
 * Created by liuyuanmao on 2019/12/3.
 */
class ContextProvider : ContentProvider() {

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalAccessException("you can't call this method<${javaClass.name}#insert>!")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        throw IllegalAccessException("you can't call this method<${javaClass.name}#query>!")
    }

    override fun onCreate(): Boolean {
        appContext = context!!.applicationContext
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw IllegalAccessException("you can't call this method<${javaClass.name}#update>!")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalAccessException("you can't call this method<${javaClass.name}#delete>!")
    }

    override fun getType(uri: Uri): String? {
        throw IllegalAccessException("you can't call this method<${javaClass.name}#getType>!")
    }

    companion object {
        @JvmStatic
        private var appContext: Context by Delegates.notNull()

        fun getApplicationContext():Context = appContext
    }
}