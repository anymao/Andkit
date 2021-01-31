package com.anymore.andkit.repository

import android.app.Application
import android.content.Context
import com.anymore.andkit.repository.di.RepositoryEntry
import dagger.hilt.EntryPoints

/**
 * Created by anymore on 2021/1/31.
 */
val Application.repositoryManager
    get() = EntryPoints.get(this, RepositoryEntry::class.java).provideIRepositoryManager()

val Context.repositoryManager get() = (applicationContext as Application).repositoryManager