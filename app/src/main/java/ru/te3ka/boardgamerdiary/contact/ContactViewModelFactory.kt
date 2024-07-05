package ru.te3ka.boardgamerdiary.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.te3ka.boardgamerdiary.repository.ContactRepository

/**
 * Фабрика для создания экземпляров ContactViewModel с параметром ContactRepository.
 */
class ContactViewModelFactory(
    private val repository: ContactRepository
) : ViewModelProvider.Factory {
    /**
     * Создает новый экземпляр ContactViewModel с предоставленным репозиторием.
     *
     * @param modelClass Класс ViewModel, который необходимо создать.
     * @return Экземпляр ContactViewModel.
     * @throws IllegalArgumentException Если передан неизвестный класс ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}