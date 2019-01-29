package com.gudino.contacts.contactsapp.model

data class Contact(val user_id: String,
                   val created_at: String,
                   val birth_date: String,
                   val first_name: String,
                   val last_name: String,
                   val phones: List<Phone>,
                   val thumb: String,
                   val photo: String,
                   val addresses: List<Address>)