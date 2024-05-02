package ru.te3ka.boardgamerdiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainMenuViewModel : ViewModel() {
    private val _navigateToProfile = MutableLiveData<Event<Unit>>()
    val navigateToProfile: LiveData<Event<Unit>> = _navigateToProfile

    fun onProfileButtonClicked() {
        _navigateToProfile.value = Event(Unit)
    }
}