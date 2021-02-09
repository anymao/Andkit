package com.anymore.andkit.di.compiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by anymore on 2021/1/31.
 */
abstract class BaseProcessor : AbstractProcessor() {

    lateinit var messager: Messager
    lateinit var logger: Logger

    var isDebug: Boolean = false
        protected set


    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) {
            return
        }
        isDebug = processingEnv.options.containsValue("debug")
        messager = processingEnv.messager
        logger = Logger(messager).apply {
            tag = this::class.qualifiedName.toString()
        }
        logger.w("初始化完成!")
    }


}