package com.gudino.contacts.contactsapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.gudino.contacts.contactsapp.ContactsViewModel
import com.gudino.contacts.contactsapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)

        viewModel.navigate.observe(this, Observer {
            showContactDetailFragment(it!!)
        })

        if (savedInstanceState == null) {
            showContactsListFragment()
        }
    }

    private fun showContactsListFragment() =
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ContactListFragment())
                    .commit()

    private fun showContactDetailFragment(userId: String) =
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ContactDetailFragment.newInstance(userId))
                    .addToBackStack(null)
                    .commit()
}
