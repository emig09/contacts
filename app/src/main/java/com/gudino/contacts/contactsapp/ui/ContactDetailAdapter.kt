package com.gudino.contacts.contactsapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gudino.contacts.contactsapp.R
import com.gudino.contacts.contactsapp.model.Address
import com.gudino.contacts.contactsapp.model.Contact
import com.gudino.contacts.contactsapp.model.Phone
import kotlinx.android.synthetic.main.contact_detail_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ContactDetailAdapter : RecyclerView.Adapter<ContactDetailAdapter.ViewHolder>() {

    private var items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDetailAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_detail_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) = with(itemView) {
            title.text = item
        }
    }

    fun loadDetail(contact: Contact) {
        items.clear()
        items.add(contact.first_name.plus(" ").plus(contact.last_name))
        items.add("Birthday: ${getBirthday(contact.birth_date)}")
        items.addAll(getPhones(contact.phones))
        items.addAll(getAddresses(contact.addresses))
        notifyDataSetChanged()
    }

    private fun getBirthday(birthDate: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(birthDate)
        return SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date)
    }

    private fun getPhones(phones: List<Phone>): List<String> {
        val data = ArrayList<String>()
        phones.forEach {
            if (it.number != null) {
                data.add(it.type.toUpperCase().plus(" ").plus(it.number))
            }
        }
        return data
    }

    private fun getAddresses(phones: List<Address>): List<String> {
        val data = ArrayList<String>()
        phones.forEach {
            it.home?.let { data.add("Home Address: $it") }
            it.work?.let { data.add("Work Address: $it") }
        }
        return data
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}