package com.anymore.andkit.di.compiler.generators

import javax.annotation.processing.Filer

/**
 * Created by anymore on 2021/2/2.
 */
interface BaseGenerator {
    val filer: Filer
    fun generate()
}