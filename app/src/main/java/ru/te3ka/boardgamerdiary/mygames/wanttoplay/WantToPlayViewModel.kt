package ru.te3ka.boardgamerdiary.mygames.wanttoplay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.dao.WantToPlayDao
import ru.te3ka.boardgamerdiary.db.WantToPlayDatabase
import ru.te3ka.boardgamerdiary.model.WantToPlay

class WantToPlayViewModel(application: Application) : AndroidViewModel(application) {
    private val wantToPlayDao: WantToPlayDao = WantToPlayDatabase.getWantToPlayDatabase(application).wantToPlatDao()
    val allWantToPlay: Flow<List<WantToPlay>> = wantToPlayDao.getAllWantToPlay()

    fun addWantToPlay(name: String) {
        val wantToPlay = WantToPlay(name = name)
        viewModelScope.launch {
            wantToPlayDao.insertWantToPlay(wantToPlay)
        }
    }

    fun updateWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.updateWantToPlay(wantToPlay)
        }
    }

    fun deleteWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.deleteWantToPlay(wantToPlay)
        }
    }
}