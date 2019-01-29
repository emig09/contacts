package com.gudino.contacts.contactsapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.Toast
import com.gudino.contacts.contactsapp.ContactsViewModel
import com.gudino.contacts.contactsapp.R
import kotlinx.android.synthetic.main.contacts_list_fragment.*


class ContactListFragment : Fragment(), ContactsListAdapter.ActionHandler {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.contacts.observe(this, Observer {
            adapter.loadContacts(it!!)
        })

        viewModel.errors.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.contacts_list_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ContactsListAdapter(this)
        contact_list.layoutManager = LinearLayoutManager(activity)
        contact_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        contact_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchMenu = menu.findItem(R.id.action_search)
        val searchView = searchMenu.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(newText: String?) = false

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.applyFilter(query)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun tap(userId: String?) {
        viewModel.navigate(userId!!)
    }
}