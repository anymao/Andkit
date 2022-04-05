package com.anymore.andkit.mvvm.ktx

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.andkit.mvvm.base.ViewModelRegister
import kotlin.reflect.KClass

/**
 * Created by anymore on 2022/4/2.
 */
@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.andkitViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return AndkitActivityViewModelLazy(this, VM::class, { viewModelStore }, factoryPromise)
}

class AndkitActivityViewModelLazy<VM : ViewModel>(
    private val activity: ComponentActivity,
    private val viewModelClass: KClass<VM>,
    private val storeProducer: () -> ViewModelStore,
    private val factoryProducer: () -> ViewModelProvider.Factory
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            val viewModel = cached
            return if (viewModel == null) {
                val factory = factoryProducer()
                val store = storeProducer()
                ViewModelProvider(store, factory).get(viewModelClass.java).also {
                    cached = it
                }.also {
                    if (activity is ViewModelRegister && it is BaseViewModel) {
                        activity.register(it)
                    }
                }
            } else {
                viewModel
            }
        }

    override fun isInitialized(): Boolean = cached != null
}