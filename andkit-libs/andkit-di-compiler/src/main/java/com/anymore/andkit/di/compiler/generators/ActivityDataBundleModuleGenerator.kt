package com.anymore.andkit.di.compiler.generators

import com.anymore.andkit.di.DataBundle
import com.anymore.andkit.di.compiler.*
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.Element

/**
 * Created by anymore on 2021/2/15.
 */
class ActivityDataBundleModuleGenerator(
    private val moduleName: String,
    override val filer: Filer
) : BaseGenerator {

    private val genPkgName: String = "com.anymore.andkit.gen"

    private val module: TypeSpec.Builder = TypeSpec.objectBuilder(
        ClassName(
            genPkgName,
            "${moduleName.capitalize()}ActivityModule"
        )
    )
        .addAnnotation(AnnotationSpec.builder(DAGGER_MODULE_ANNOTATION).build())
        .addAnnotation(
            AnnotationSpec.builder(HILT_INSTALL_IN_ANNOTATION)
                .addMember("value=[%T::class]", ACTIVITY_COMPONENT_CLASS).build()
        )

    private fun provideActivityDataBundleFunSpec(activityElement: Element): FunSpec {
        val clazz = activityElement.getValueOf(DataBundle::class.java, "clazz")?.let {
            it.asTypeName()
        } ?: throw RuntimeException("${DataBundle::class.simpleName}'clazz must not null")
        return FunSpec.builder("provide_${clazz.toString().replace(".", "_")}")
            .addAnnotation(AnnotationSpec.builder(DAGGER_PROVIDES_ANNOTATION).build())
            .addAnnotation(AnnotationSpec.builder(ACTIVITY_SCOPED_ANNOTATION).build())
            .addParameter("activity", ACTIVITY_CLASS)
            .returns(clazz)
            .addCode(
                CodeBlock.builder()
                    .add("return activity.intent?.getParcelableExtra(%S)?: %T()", EXTRA, clazz)
                    .build()
            )
            .build()
    }


    fun addActivityDataBundleElement(element: Element) {
        module.addFunction(provideActivityDataBundleFunSpec(element))
    }

    override fun generate() {
        FileSpec.builder(genPkgName, "${moduleName.capitalize()}ActivityModule")
            .addType(module.build())
            .build()
            .writeTo(filer)

    }
}