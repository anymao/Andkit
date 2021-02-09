package com.anymore.andkit.di.compiler

import com.anymore.andkit.di.RetrofitService
import com.anymore.andkit.di.RoomDao
import com.google.auto.service.AutoService
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedAnnotationTypes(value = [SUPPORT_ANNOTATION_RETROFIT_SERVICE, SUPPORT_ANNOTATION_ROOM_DAO])
class AndkitDiProcessor : BaseProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations?.isNotEmpty() == true && roundEnv != null) {
            logger.v("开始处理注解:$annotations")
            processRetrofitService(annotations, roundEnv)
            processRoomDao(annotations, roundEnv)
            return true
        }
        return false
    }


    private fun processRetrofitService(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        logger.v("开始处理注解:${RetrofitService::class.qualifiedName}")
        val retrofitServiceElements = roundEnv.getElementsAnnotatedWith(RetrofitService::class.java)
        retrofitServiceElements.forEach {
            logger.e("找到：${it.simpleName}")
        }
    }

    private fun processRoomDao(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ) {
        logger.v("开始处理注解:${RoomDao::class.qualifiedName}")
        val retrofitServiceElements = roundEnv.getElementsAnnotatedWith(RoomDao::class.java)
        retrofitServiceElements.forEach {
            logger.e("找到：${it.simpleName}")
            AndkitGenerator("CodeLove").generate()
        }
    }

}