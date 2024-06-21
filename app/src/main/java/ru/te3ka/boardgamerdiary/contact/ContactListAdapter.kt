package ru.te3ka.boardgamerdiary.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.databinding.CustomElementListContactLayoutBinding
import ru.te3ka.boardgamerdiary.model.Contact

class ContactListAdapter(private var contacts: List<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: CustomElementListContactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.editTextContactContactPhone.setText(contact.phone)
            binding.editTextContactNickname.setText(contact.nickname)
            binding.editTextContactFirstName.setText(contact.firstName)
            binding.editTextContactSurname.setText(contact.surname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = CustomElementListContactLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}