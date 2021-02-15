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
            println("$tag=>[$message]\n")
            messager.printMessage(Diagnostic.Kind.NOTE, "$tag=>[$message]\n")
        }
    }

    fun w(message: String?) {
        if (message.isNotBlank()) {
            System.err.println("$tag=>[$message]\n")
            messager.printMessage(Diagnostic.Kind.WARNING, "$tag=>[$message]\n")
        }
    }

    fun e(message: String?) {
        if (message.isNotBlank()) {
            System.err.println("$tag=>[$message]\n")
            messager.printMessage(Diagnostic.Kind.ERROR, "$tag=>[$message]\n")
        }
    }

    fun e(throwable: Throwable?) {
        if (throwable != null) {
            throwable.printStackTrace(System.err)
            messager.printMessage(Diagnostic.Kind.ERROR, "$tag=>[ERROR:${throwable.message}]\n")
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