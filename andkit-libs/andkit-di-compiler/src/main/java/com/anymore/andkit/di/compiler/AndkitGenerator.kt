package com.anymore.andkit.di.compiler

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * Created by anymore on 2021/2/2.
 */
internal class AndkitGenerator(val className: String) : BaseGenerator {


    override fun generate() {
        val showTextMethodBlock = CodeBlock.builder()
            .addStatement("\$T.out.println(\$S)", System::class.java, "text")
            .addStatement("return text")
            .build()
        val showTextMethod = MethodSpec.methodBuilder("showText")
            .addModifiers(Modifier.PUBLIC)
            .returns(String::class.java)
            .addParameter(String::class.java, "text")
            .addStatement(showTextMethodBlock)
            .build()
        val typeSpec = TypeSpec.classBuilder(className)
            .addMethod(showTextMethod)
            .build()
        val javaFile = JavaFile.builder("com.anymore.andkit.di", typeSpec).build()
        javaFile.writeTo(System.out)

    }


}