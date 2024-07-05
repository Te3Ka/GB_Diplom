package ru.te3ka.boardgamerdiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel для главного меню приложения. Хранит состояние навигации и обрабатывает
 * события, связанные с переходами между фрагментами.
 */
class MainMenuViewModel : ViewModel() {
    private val _navigateToDestination = MutableLiveData<Event<Int>>()
    val navigateToDestination: LiveData<Event<Int>> = _navigateToDestination

    /**
     * Вызывается при нажатии на кнопку навигации.
     * @param destinationId Идентификатор назначения для навигации
     */
    fun onNavigationButtonClicked(destinationId: Int) {
        _navigateToDestination.value = Event(destinationId)
    }
}