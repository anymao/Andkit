package com.anymore.andkit.mvvm.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.anymore.andkit.mvvm.base.BaseViewModel
import com.anymore.andkit.mvvm.base.ViewModelRegister
import kotlin.reflect.KClass

/**
 * Created by anymore on 2022/4/2.
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.andkitViewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = andkitCreateViewModelLazy(
    VM::class, { ownerProducer().viewModelStore },
    factoryProducer ?: {
        (ownerProducer() as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
            ?: defaultViewModelProviderFactory
    }
)


@MainThread
inline fun <reified VM : ViewModel> Fragment.andkitActivityViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = andkitCreateViewModelLazy(
    VM::class, { requireActivity().viewModelStore },
    factoryProducer ?: { requireActivity().defaultViewModelProviderFactory }
)

/**
 * Helper method for creation of [ViewModelLazy], that resolves `null` passed as [factoryProducer]
 * to default factory.
 */
@MainThread
fun <VM : ViewModel> Fragment.andkitCreateViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return AndkitFragmentViewModelLazy(this, viewModelClass, storeProducer, factoryPromise)
}

class AndkitFragmentViewModelLazy<VM : ViewModel>(
    private val fragment: Fragment,
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
                    if (fragment is ViewModelRegister && it is BaseViewModel) {
                        fragment.register(it)
                    }
                }
            } else {
                viewModel
            }
        }

    override fun isInitialized(): Boolean = cached != null
}