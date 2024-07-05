package ru.te3ka.boardgamerdiary.repository

import androidx.lifecycle.LiveData
import ru.te3ka.boardgamerdiary.dao.ContactDao
import ru.te3ka.boardgamerdiary.model.Contact

/**
 * Репозиторий для работы с данными контактов.
 * Обеспечивает доступ к операциям вставки, обновления и удаления контактов в базе данных.
 *
 * @param contactDao DAO для работы с таблицей контактов в базе данных.
 */
class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    /**
     * Вставляет новый контакт в базу данных.
     * @param contact Контакт, который нужно вставить.
     */
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    /**
     * Обновляет информацию о существующем контакте в базе данных.
     * @param contact Контакт с обновленными данными.
     */
    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    /**
     * Удаляет контакт из базы данных.
     * @param contact Контакт, который нужно удалить.
     */
    suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }
}