package ru.te3ka.boardgamerdiary.contact

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkContact
import ru.te3ka.boardgamerdiary.repository.ContactRepository
import ru.te3ka.boardgamerdiary.service.RetrofitClient

/**
 * ViewModel для управления данными контактов и взаимодействием с репозиторием.
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContacts: LiveData<List<Contact>> = repository.allContacts

    /**
     * Добавляет новый контакт в базу данных и загружает его на сервер.
     */
    fun addContact(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
        uploadContact(convertToNetworkContact(contact))
    }

    /**
     * Преобразует локальный объект Contact в объект NetworkContact для отправки на сервер.
     */
    private fun convertToNetworkContact(contact: Contact): NetworkContact {
        return NetworkContact(
            id = contact.id,
            phone = contact.phone,
            nickname = contact.nickname,
            firstName = contact.firstName,
            surname = contact.surname
        )
    }

    /**
     * Загружает контакт на сервер.
     */
    private fun uploadContact(contact: NetworkContact) {
        RetrofitClient.apiService.uploadContact(contact).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Contact uploaded successfully")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Network error: ${t.message}")
            }
        })
    }

    /**
     * Обновляет существующий контакт в базе данных и загружает его на сервер.
     */
    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
            uploadContact(convertToNetworkContact(contact))
        }
    }

    /**
     * Удаляет контакт из базы данных.
     */
    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
}