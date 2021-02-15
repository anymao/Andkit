package com.anymore.andkit.di.compiler

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror

/**
 * Created by anymore on 2021/2/15.
 */
private fun getEventTypeAnnotationMirror(typeElement: Element, clazz: Class<*>): AnnotationMirror? {
    val clazzName = clazz.name
    for (m in typeElement.annotationMirrors) {
        if (m.annotationType.toString() == clazzName) {
            return m
        }
    }
    return null
}

private fun getAnnotationValue(annotationMirror: AnnotationMirror, key: String): AnnotationValue? {
    for ((key1, value) in annotationMirror.elementValues) {
        if (key1!!.simpleName.toString() == key) {
            return value
        }
    }
    return null
}

internal fun Element.getValueOf(clazz: Class<*>, valueName: String): TypeMirror? {
    val am = getEventTypeAnnotationMirror(this, clazz) ?: return null
    val av = getAnnotationValue(am, valueName)
    return if (av == null) {
        null
    } else {
        av.value as TypeMirror
    }
}