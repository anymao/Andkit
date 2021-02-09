package com.anymore.andkit.di.compiler

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

/**
 * Created by anymore on 2021/1/31.
 */
class Logger(val messager: Messager) {
    var tag = "Processor-Logger"

    fun v(message: String?) {
        if (message.isNotBlank()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "$tag=>[$message]")
        }
    }

    fun w(message: String?) {
        if (message.isNotBlank()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "$tag=>[$message]")
        }
    }

    fun e(message: String?) {
        if (message.isNotBlank()) {
            messager.printMessage(Diagnostic.Kind.ERROR, "$tag=>[$message]")
        }
    }

    fun e(throwable: Throwable?) {
        if (throwable != null) {
            messager.printMessage(Diagnostic.Kind.ERROR, "$tag=>[ERROR:${throwable.message}]")
        }
    }


    companion object {
        fun String?.isNotBlank() = !isNullOrBlank()
        fun String?.orDefault(default: String) = if (isNullOrBlank()) {
            default
        } else {
            this
        }
    }

}