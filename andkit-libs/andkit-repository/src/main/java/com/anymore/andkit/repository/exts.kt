package com.anymore.andkit.repository

import android.app.Application
import android.content.Context
import com.anymore.andkit.repository.di.component.RepositoryComponent

/**
 * Created by liuyuanmao on 2020/1/16.
 */
val Context.repositoryComponent: RepositoryComponent
    get() = (this.applicationContext as Application).repositoryComponent


val Application.repositoryComponent: RepositoryComponent
    get() {
        if (this is IRepositoryComponentProvider) {
            return repositoryComponent
        } else {
            throw RuntimeException("the Application must be Implementation class of IRepositoryComponentProvider")
        }
    }