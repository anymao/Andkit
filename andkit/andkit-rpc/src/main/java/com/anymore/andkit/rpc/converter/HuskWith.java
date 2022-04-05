package com.anymore.andkit.rpc.converter;

import com.anymore.andkit.rpc.ResponseWrapper;

/**
 * 这个类用kotlin写就编译失败
 * Created by anymore on 2022/4/2.
 */
public @interface HuskWith {

    @SuppressWarnings("rawtypes")
    Class<? extends ResponseWrapper> value();

    long[] codes() default {0};
}
