package com.anymore.andkit.di.compiler.generators

import com.anymore.andkit.di.compiler.*
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.Element

/**
 * Created by anymore on 2021/2/15.
 */
class RepositoryConfigurationModuleGenerator(
    private val pkgName: String,
    private val element: Element,
    override val filer: Filer
) : BaseGenerator {

    private fun provideFunc(): FunSpec {
        return FunSpec.builder("provide${element.simpleName}")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(
                AnnotationSpec.builder(DAGGER_BIND_ANNOTATION).build()
            )
            .addAnnotation(AnnotationSpec.builder(SINGLETON_ANNOTATION).build())
            .addParameter("configuration", ClassName(pkgName, element.simpleName.toString()))
            .returns(REPOSITORY_CONFIG_CLASS)
            .build()
    }


    override fun generate() {
        val type = TypeSpec.classBuilder(ClassName(pkgName, "${element.simpleName}Module"))
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(AnnotationSpec.builder(DAGGER_MODULE_ANNOTATION).build())
            .addAnnotation(
                AnnotationSpec.builder(HILT_INSTALL_IN_ANNOTATION)
                    .addMember("value=[%T::class]", SINGLETON_COMPONENT_CLASS).build()
            )
            .addFunction(provideFunc()).build()
        FileSpec.builder(pkgName, "${element.simpleName}Module")
            .addComment("Auto generate by AndkitDiProcessor，Don't modify the file by hand!!!")
            .addType(type)
            .build()
            .writeTo(filer)
    }
}


