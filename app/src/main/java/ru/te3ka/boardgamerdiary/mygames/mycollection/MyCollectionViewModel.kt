package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.te3ka.boardgamerdiary.dao.MyCollectionDao
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkMyCollection
import ru.te3ka.boardgamerdiary.service.RetrofitClient

class MyCollectionViewModel(application: Application) : AndroidViewModel(application) {
    private val myCollectionDao: MyCollectionDao =
        BgdDatabase.getDatabase(application).myCollectionDao()
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
            uploadMyCollection(convertToNetworkMyCollection(myCollectionGame))
        }
    }

    fun addMyCollection(boardgame: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.insertMyCollection(boardgame)
            uploadMyCollection(convertToNetworkMyCollection(boardgame))
        }
    }

    fun updateMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.updateMyCollection(myCollection)
            uploadMyCollection(convertToNetworkMyCollection(myCollection))
        }
    }

    fun deleteMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.deleteMyCollection(myCollection)
        }
    }

    private fun convertToNetworkMyCollection(myCollection: MyCollection): NetworkMyCollection {
        return NetworkMyCollection(
            name = myCollection.name,
            score = myCollection.score,
            numberOfGames = myCollection.numberOfGames,
            monthOfPurchase = myCollection.monthOfPurchase,
            yearOfPurchase = myCollection.yearOfPurchase
        )
    }

    private fun uploadMyCollection(networkMyCollection: NetworkMyCollection) {
        RetrofitClient.apiService.uploadMyCollection(networkMyCollection).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "MyCollection uploaded successfully")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Network error: ${t.message}")
            }
        })
    }
}