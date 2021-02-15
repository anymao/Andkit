package com.anymore.andkit.di.compiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * Created by anymore on 2021/1/31.
 */
abstract class BaseProcessor : AbstractProcessor() {

    lateinit var messager: Messager
    lateinit var logger: Logger
    lateinit var filer: Filer
    lateinit var elementUtils: Elements
    lateinit var typeUtils: Types
    var moduleName: String = ""

    var isDebug: Boolean = false
        protected set


    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) {
            return
        }
        moduleName = processingEnv.options[SUPPORT_OPTION_MODULE_NAME].orEmpty()
        isDebug = processingEnv.options.containsValue("debug")
        messager = processingEnv.messager
        filer = processingEnv.filer
        elementUtils = processingEnv.elementUtils
        typeUtils = processingEnv.typeUtils
        logger = Logger(messager).apply {
            tag = this@BaseProcessor::class.qualifiedName.toString()
        }
        logger.v("初始化完成![${processingEnv.options}]")
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

}