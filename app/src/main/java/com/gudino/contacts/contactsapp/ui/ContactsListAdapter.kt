package com.gudino.contacts.contactsapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gudino.contacts.contactsapp.R
import com.gudino.contacts.contactsapp.model.Contact
import kotlinx.android.synthetic.main.contact_item_main.view.*


class ContactsListAdapter(private val actionHandler: ActionHandler)
    : RecyclerView.Adapter<ContactsListAdapter.ViewHolder>() {

    interface ActionHandler {
        fun tap(userId: String?)
    }

    private var contacts: List<Contact> = ArrayList()
    private var contactListFiltered: List<Contact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_main, parent, false))

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position], actionHandler)
    }

    fun loadContacts(contactList: List<Contact>) {
        contacts = ArrayList(contactList)
        contactListFiltered = contacts
        notifyDataSetChanged()
    }

    fun applyFilter(query: String?) {
        if (query.isNullOrBlank()) {
            contacts = contactListFiltered
            notifyDataSetChanged()
        } else {
            val data = ArrayList<Contact>()
            contactListFiltered.forEach {
                if (it.first_name.toLowerCase().contains(query.toString()) || it.last_name.toLowerCase().contains(query.toString())) {
                    data.add(it)
                }
                contacts = data
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(contact: Contact?, actionHandler: ActionHandler) = with(itemView) {
            Glide.with(itemView.context)
                    .load(contact?.thumb)
                    .apply(RequestOptions.circleCropTransform().placeholder(itemView.context.getDrawable(R.drawable.person_placeholder)))
                    .into(thumbnail)
            name.text = contact?.first_name.plus(" ").plus(contact?.last_name)
            setOnClickListener {
                actionHandler.tap(contact?.user_id)
            }
        }
    }
}