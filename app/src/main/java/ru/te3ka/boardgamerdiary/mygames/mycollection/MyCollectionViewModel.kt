package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.dao.MyCollectionDao
import ru.te3ka.boardgamerdiary.db.MyCollectionDatabase
import ru.te3ka.boardgamerdiary.model.MyCollection

class MyCollectionViewModel(application: Application) : AndroidViewModel(application) {
    private val myCollectionDao: MyCollectionDao =
        MyCollectionDatabase.getMyCollectionDatabase(application).myCollectionDao()
    val allMyCollection: Flow<List<MyCollection>> = myCollectionDao.getAllMyCollection()

    fun addMyCollection(
        name: String,
        score: String,
        numberOfGames: String,
        yearOfPurchase: String,
        monthOfPurchase: String
    ) {
        val myCollectionGame = MyCollection(
            name = name,
            score = score,
            numberOfGames = numberOfGames,
            yearOfPurchase = yearOfPurchase,
            monthOfPurchase = monthOfPurchase
        )
        viewModelScope.launch {
            myCollectionDao.insertMyCollection(myCollectionGame)
        }
    }

    fun addMyCollection(boardgame: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.insertMyCollection(boardgame)
        }
    }

    fun updateMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.updateMyCollection(myCollection)
        }
    }

    fun deleteMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.deleteMyCollection(myCollection)
        }
    }
}