package com.gudino.contacts.contactsapp

import com.gudino.contacts.contactsapp.model.Contact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApi {

    @GET("/contacts")
    fun listContacts(): Call<List<Contact>>

    @GET("/contacts/{id}")
    fun contact(@Path("id") contactId: String): Call<Contact>
}