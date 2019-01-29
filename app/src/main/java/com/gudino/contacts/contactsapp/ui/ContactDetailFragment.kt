package com.gudino.contacts.contactsapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gudino.contacts.contactsapp.ContactsViewModel
import com.gudino.contacts.contactsapp.R
import kotlinx.android.synthetic.main.contact_detail.*

class ContactDetailFragment : Fragment() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactDetailAdapter

    companion object {
        private const val USER_ID = "USER_ID"

        fun newInstance(userId: String) = ContactDetailFragment().apply {
            arguments = Bundle().apply {
                putString(USER_ID, userId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.loadContactDetail(arguments?.getString(USER_ID))

        viewModel.contactDetail.observe(this, Observer {
            it?.let {
                Glide.with(this)
                        .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.person_placeholder))
                        .load(it.photo)
                        .into(photo)
                adapter.loadDetail(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contact_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactDetailAdapter()
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        list.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        adapter.clear()
    }
}