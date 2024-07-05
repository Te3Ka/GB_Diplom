package ru.te3ka.boardgamerdiary.mygames.mycollection

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

/**
 * ViewModel для управления данными о коллекциях игр и взаимодействия с репозиторием.
 *
 * @param application Приложение, в контексте которого создается ViewModel.
 */
class MyCollectionViewModel(application: Application) : AndroidViewModel(application) {
    private val myCollectionDao: MyCollectionDao =
        BgdDatabase.getDatabase(application).myCollectionDao()
    val allMyCollection: Flow<List<MyCollection>> = myCollectionDao.getAllMyCollection()

    /**
     * Добавляет новую коллекцию игр в базу данных и на сервер.
     *
     * @param name Название игры.
     * @param score Оценка игры.
     * @param numberOfGames Количество игр.
     * @param yearOfPurchase Год покупки.
     * @param monthOfPurchase Месяц покупки.
     */
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

    /**
     * Добавляет новую коллекцию игр в базу данных и на сервер.
     *
     * @param boardgame Коллекция игр.
     */
    fun addMyCollection(boardgame: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.insertMyCollection(boardgame)
            uploadMyCollection(convertToNetworkMyCollection(boardgame))
        }
    }

    /**
     * Обновляет существующую коллекцию игр в базе данных и на сервере.
     *
     * @param myCollection Обновленная коллекция игр.
     */
    fun updateMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.updateMyCollection(myCollection)
            uploadMyCollection(convertToNetworkMyCollection(myCollection))
        }
    }

    /**
     * Удаляет коллекцию игр из базы данных.
     *
     * @param myCollection Коллекция игр для удаления.
     */
    fun deleteMyCollection(myCollection: MyCollection) {
        viewModelScope.launch {
            myCollectionDao.deleteMyCollection(myCollection)
        }
    }

    /**
     * Преобразует объект MyCollection в NetworkMyCollection.
     *
     * @param myCollection Коллекция игр.
     * @return Объект NetworkMyCollection.
     */
    private fun convertToNetworkMyCollection(myCollection: MyCollection): NetworkMyCollection {
        return NetworkMyCollection(
            name = myCollection.name,
            score = myCollection.score,
            numberOfGames = myCollection.numberOfGames,
            monthOfPurchase = myCollection.monthOfPurchase,
            yearOfPurchase = myCollection.yearOfPurchase
        )
    }

    /**
     * Загружает коллекцию игр на сервер.
     *
     * @param networkMyCollection Коллекция игр для загрузки.
     */
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