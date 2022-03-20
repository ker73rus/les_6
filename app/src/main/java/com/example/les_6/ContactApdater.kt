package com.example.les_6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contacts: List<String>) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contactName: TextView = itemView.findViewById(R.id.contactName)

        fun bind(contact: String) {
            contactName.text = contact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactsViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}