package ru.te3ka.boardgamerdiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator

class MainMenuViewModel : ViewModel() {
    private val _navigateToDestination = MutableLiveData<Event<Int>>()
    val navigateToDestination: LiveData<Event<Int>> = _navigateToDestination

    fun onNavigationButtonClicked(destinationId: Int) {
        _navigateToDestination.value = Event(destinationId)
    }
}