package ru.te3ka.boardgamerdiary.mygames.wanttoplay

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
import ru.te3ka.boardgamerdiary.dao.WantToPlayDao
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWantToPlay
import ru.te3ka.boardgamerdiary.service.RetrofitClient

/**
 * ViewModel для управления данными о желаемых играх и их синхронизацией с сервером.
 *
 * @property wantToPlayDao DAO для доступа к данным о желаемых играх
 * @property allWantToPlay Поток данных всех желаемых игр из базы данных
 */
class WantToPlayViewModel(application: Application) : AndroidViewModel(application) {
    private val wantToPlayDao: WantToPlayDao = BgdDatabase.getDatabase(application).wantToPlayDao()
    val allWantToPlay: Flow<List<WantToPlay>> = wantToPlayDao.getAllWantToPlay()

    /**
     * Добавляет новую желаемую игру в базу данных и отправляет данные на сервер.
     *
     * @param name Название желаемой игры
     */
    fun addWantToPlay(name: String) {
        val wantToPlay = WantToPlay(name = name)
        viewModelScope.launch {
            wantToPlayDao.insertWantToPlay(wantToPlay)
            uploadWantToPlay(convertToNetworkWantToPlay(wantToPlay))
        }
    }

    /**
     * Обновляет информацию о желаемой игре в базе данных и отправляет обновленные данные на сервер.
     *
     * @param wantToPlay Объект желаемой игры с обновленными данными
     */
    fun updateWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.updateWantToPlay(wantToPlay)
            uploadWantToPlay(convertToNetworkWantToPlay(wantToPlay))
        }
    }

    /**
     * Удаляет желаемую игру из базы данных.
     *
     * @param wantToPlay Объект желаемой игры, который нужно удалить
     */
    fun deleteWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.deleteWantToPlay(wantToPlay)
        }
    }

    /**
     * Преобразует объект `WantToPlay` в сетевой формат `NetworkWantToPlay`.
     *
     * @param wantToPlay Объект желаемой игры
     * @return Объект `NetworkWantToPlay` с данными желаемой игры
     */
    private fun convertToNetworkWantToPlay(wantToPlay: WantToPlay): NetworkWantToPlay {
        return NetworkWantToPlay(
            name = wantToPlay.name,
        )
    }

    /**
     * Отправляет данные о желаемой игре на сервер.
     *
     * @param networkWantToPlay Данные о желаемой игре в сетевом формате
     */
    private fun uploadWantToPlay(networkWantToPlay: NetworkWantToPlay) {
        RetrofitClient.apiService.uploadWantToPlay(networkWantToPlay).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "WantToPlay uploaded successfully")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Network error: ${t.message}")
            }
        })
    }
}