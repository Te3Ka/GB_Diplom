package ru.te3ka.boardgamerdiary.repository

import androidx.lifecycle.LiveData
import ru.te3ka.boardgamerdiary.dao.ContactDao
import ru.te3ka.boardgamerdiary.model.Contact

class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }
}