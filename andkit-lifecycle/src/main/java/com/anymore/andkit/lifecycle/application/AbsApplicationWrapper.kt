package com.anymore.andkit.lifecycle.application

import android.app.Application
import android.content.ContextWrapper

/**
 * 代理[Application]的基础行为,应用程序只拥有一个真正的[Application]
 * 但是可以有多个[AbsApplicationWrapper],多个模块间可以继承实现自己的[AbsApplicationWrapper]
 * 并且把它当做[Application]来进行初始化，最后通过[Kiss]或者[Kisses]注解将它标记在真正的应用程序的
 * [Application]上，[ApplicationWrapperMonitor]会在相应
 * Created by liuyuanmao on 2019/11/4.
 */
abstract class AbsApplicationWrapper(val application: Application) : ContextWrapper(application),
    IApplicationLifecycle