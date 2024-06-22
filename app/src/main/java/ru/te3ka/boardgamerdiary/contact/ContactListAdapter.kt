package ru.te3ka.boardgamerdiary.contact

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.te3ka.boardgamerdiary.databinding.CustomElementListContactLayoutBinding
import ru.te3ka.boardgamerdiary.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactListAdapter(
    private var contacts: MutableList<Contact>,
    private val viewModel: ContactViewModel
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(
        val binding: CustomElementListContactLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentContact: Contact

        init {
            binding.editTextContactNickname.addTextChangedListener(
                createTextWatcher(
                    "nickname",
                    adapterPosition
                )
            )
            binding.editTextContactContactPhone.addTextChangedListener(
                createTextWatcher(
                    "phone",
                    adapterPosition
                )
            )
            binding.editTextContactFirstName.addTextChangedListener(
                createTextWatcher(
                    "firstName",
                    adapterPosition
                )
            )
            binding.editTextContactSurname.addTextChangedListener(
                createTextWatcher(
                    "surname",
                    adapterPosition
                )
            )

            binding.root.setOnLongClickListener {
                deleteContact(adapterPosition)
                true
            }
        }

        fun bind(contact: Contact) {
            currentContact = contact
            binding.editTextContactContactPhone.setText(contact.phone)
            binding.editTextContactNickname.setText(contact.nickname)
            binding.editTextContactFirstName.setText(contact.firstName)
            binding.editTextContactSurname.setText(contact.surname)
        }
    }


    private fun createTextWatcher(fieldName: String, position: Int): TextWatcher {
        return object : TextWatcher {
            private val handler = Handler(Looper.getMainLooper())
            private var runnable: Runnable? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                runnable?.let { handler.removeCallbacks(it) }
                runnable = Runnable {
                    if (position != RecyclerView.NO_POSITION && position < contacts.size) {
                        val contact = contacts[position]
                        val updatedContact = when (fieldName) {
                            "phone" -> contact.copy(phone = s?.toString() ?: "")
                            "nickname" -> contact.copy(nickname = s?.toString() ?: "")
                            "firstName" -> contact.copy(firstName = s?.toString() ?: "")
                            "surname" -> contact.copy(surname = s?.toString() ?: "")
                            else -> contact
                        }
                        viewModel.updateContact(updatedContact)
                    }
                }
                handler.postDelayed(runnable!!, 1500)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = CustomElementListContactLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])

        holder.itemView.apply {
            holder.binding.editTextContactNickname.addTextChangedListener(createTextWatcher("nickname", position))
            holder.binding.editTextContactContactPhone.addTextChangedListener(createTextWatcher("phone", position))
            holder.binding.editTextContactFirstName.addTextChangedListener(createTextWatcher("firstName", position))
            holder.binding.editTextContactSurname.addTextChangedListener(createTextWatcher("surname", position))
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateContacts(newContacts: List<Contact>) {
        val diffCallback = ContactDiffCallback(contacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        contacts.clear()
        contacts.addAll(newContacts)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun deleteContact(position: Int) {
        val contactToDelete = contacts[position]
        viewModel.deleteContact(contactToDelete)
    }

    override fun getItemId(position: Int): Long {
        return contacts[position].phone.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}