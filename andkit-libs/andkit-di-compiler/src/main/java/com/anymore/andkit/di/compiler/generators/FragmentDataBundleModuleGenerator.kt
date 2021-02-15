package com.anymore.andkit.di.compiler.generators

import com.anymore.andkit.di.DataBundle
import com.anymore.andkit.di.compiler.*
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.Element

/**
 * Created by anymore on 2021/2/15.
 */
class FragmentDataBundleModuleGenerator(
    private val moduleName: String,
    override val filer: Filer
) : BaseGenerator {

    private val genPkgName: String = "com.anymore.andkit.gen"

    private val module: TypeSpec.Builder = TypeSpec.objectBuilder(
        ClassName(
            genPkgName,
            "${moduleName.capitalize()}FragmentModule"
        )
    )
        .addAnnotation(AnnotationSpec.builder(DAGGER_MODULE_ANNOTATION).build())
        .addAnnotation(
            AnnotationSpec.builder(HILT_INSTALL_IN_ANNOTATION)
                .addMember("value=[%T::class]", FRAGMENT_COMPONENT_CLASS).build()
        )

    private fun provideFragmentDataBundleFunSpec(fragmentElement: Element): FunSpec {
        val clazz = fragmentElement.getValueOf(DataBundle::class.java, "clazz")?.let {
            it.asTypeName()
        } ?: throw RuntimeException("${DataBundle::class.simpleName}'clazz must not null")
        return FunSpec.builder("provide_${clazz.toString().replace(".", "_")}")
            .addAnnotation(AnnotationSpec.builder(DAGGER_PROVIDES_ANNOTATION).build())
            .addAnnotation(AnnotationSpec.builder(FRAGMENT_SCOPED_ANNOTATION).build())
            .addParameter("fragment", FRAGMENT_CLASS)
            .returns(clazz)
            .addCode(
                CodeBlock.builder()
                    .add(
                        "return fragment.arguments?.getParcelable(%S)?: %T()",
                        EXTRA,
                        clazz
                    )
                    .build()
            )
            .build()
    }


    fun addFragmentDataBundleElement(element: Element) {
        module.addFunction(provideFragmentDataBundleFunSpec(element))
    }

    override fun generate() {
        FileSpec.builder(genPkgName, "${moduleName.capitalize()}FragmentModule")
            .addType(module.build())
            .build()
            .writeTo(filer)

    }
}