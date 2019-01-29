package com.gudino.contacts.contactsapp

import android.arch.lifecycle.ViewModel
import com.gudino.contacts.contactsapp.di.DaggerViewModelInjector
import com.gudino.contacts.contactsapp.di.NetworkModule
import com.gudino.contacts.contactsapp.di.ViewModelInjector

open class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ContactsViewModel -> injector.inject(this)
        }
    }
}