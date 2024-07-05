package ru.te3ka.boardgamerdiary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.te3ka.boardgamerdiary.model.Contact

/**
 * DAO (Data Access Object) для работы с таблицей контактов в базе данных.
 */
@Dao
interface ContactDao {
    /**
     * Возвращает все контакты из таблицы.
     * @return LiveData список всех контактов.
     */
    @Query("SELECT * FROM contacts")
    fun getAllContacts() : LiveData<List<Contact>>

    /**
     * Вставляет новый контакт в таблицу. Если контакт уже существует, он заменяется.
     * @param contact Вставляемый контакт.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    /**
     * Вставляет контакт в таблицу. Если контакт уже существует, он заменяется.
     * @param contact Вставляемый контакт.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    /**
     * Обновляет существующий контакт в таблице.
     * @param contact Обновляемый контакт.
     */
    @Update
    suspend fun updateContact(contact: Contact)

    /**
     * Удаляет контакт из таблицы.
     * @param contact Удаляемый контакт.
     */
    @Delete
    suspend fun deleteContact(contact: Contact)

    /**
     * Возвращает контакт по его ID.
     * @param contactId ID контакта.
     * @return Контакт или null, если контакт с указанным ID не найден.
     */
    @Query("SELECT * FROM contacts WHERE id =:contactId")
    suspend fun getContactById(contactId: Int): Contact?
}