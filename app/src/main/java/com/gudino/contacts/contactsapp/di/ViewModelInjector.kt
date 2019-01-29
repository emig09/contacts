package com.gudino.contacts.contactsapp.di

import com.gudino.contacts.contactsapp.ContactsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ContactsViewModel.
     * @param contactsViewModel ContactsViewModel in which to inject the dependencies
     */
    fun inject(contactsViewModel: ContactsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}