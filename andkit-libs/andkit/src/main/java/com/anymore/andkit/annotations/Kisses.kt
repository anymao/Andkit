package com.anymore.andkit.annotations

/**
 * Created by liuyuanmao on 2019/11/4.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Kisses(vararg val value: Kiss)
