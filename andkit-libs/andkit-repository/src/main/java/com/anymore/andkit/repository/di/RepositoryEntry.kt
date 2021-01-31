package com.anymore.andkit.repository.di

import com.anymore.andkit.repository.IRepositoryManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by anymore on 2021/1/31.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepositoryEntry {

    fun provideIRepositoryManager(): IRepositoryManager
}