package com.gudino.contacts.contactsapp

import android.arch.lifecycle.MutableLiveData
import com.gudino.contacts.contactsapp.model.Contact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class ContactsViewModel : BaseViewModel() {

    @Inject
    lateinit var contactsApi: ContactsApi
    val contacts = MutableLiveData<List<Contact>>()
    val contactDetail = MutableLiveData<Contact>()
    val errors = MutableLiveData<String>()
    val navigate = MutableLiveData<String>()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        contactsApi.listContacts().enqueue(object : Callback<List<Contact>> {
            override fun onFailure(call: Call<List<Contact>>?, t: Throwable?) {
                when (t) {
                    is UnknownHostException -> errors.value = "no connectivity"
                    else -> errors.value = "unknown error"
                }
            }

            override fun onResponse(call: Call<List<Contact>>?, response: Response<List<Contact>>) {
                contacts.value = response.body()?.toList()?.sortedWith(compareBy { it.first_name })
            }
        })
    }

    fun navigate(userId: String) {
        navigate.value = userId
    }

    fun refresh() {
        loadContacts()
    }

    fun loadContactDetail(userId: String?) {
        if (userId != null) {
            contactsApi.contact(userId).enqueue(object : Callback<Contact> {
                override fun onFailure(call: Call<Contact>?, t: Throwable?) {
                    when (t) {
                        is UnknownHostException -> errors.value = "no connectivity"
                        else -> errors.value = "unknown error"
                    }
                }

                override fun onResponse(call: Call<Contact>?, response: Response<Contact>) {
                    contactDetail.value = response.body()
                }
            })
        }
    }
}