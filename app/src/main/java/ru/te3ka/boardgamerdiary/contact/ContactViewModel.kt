package ru.te3ka.boardgamerdiary.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.repository.ContactRepository

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContacts: LiveData<List<Contact>> = repository.allContacts

    fun addContact(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
}