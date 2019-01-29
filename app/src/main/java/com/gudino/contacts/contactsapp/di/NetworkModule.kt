package com.gudino.contacts.contactsapp.di

import com.gudino.contacts.contactsapp.ContactsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    private const val BASE_URL = "https://private-d0cc1-iguanafixtest.apiary-mock.com/"

    /**
     * Provides the Contacts service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Contacts service implementation.
     */
    @Provides
    internal fun provideLaptopsApi(retrofit: Retrofit): ContactsApi {
        return retrofit.create(ContactsApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}