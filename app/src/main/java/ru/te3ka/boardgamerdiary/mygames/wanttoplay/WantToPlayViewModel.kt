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
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkMyCollection
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWantToPlay
import ru.te3ka.boardgamerdiary.service.RetrofitClient

class WantToPlayViewModel(application: Application) : AndroidViewModel(application) {
    private val wantToPlayDao: WantToPlayDao = BgdDatabase.getDatabase(application).wantToPlayDao()
    val allWantToPlay: Flow<List<WantToPlay>> = wantToPlayDao.getAllWantToPlay()

    fun addWantToPlay(name: String) {
        val wantToPlay = WantToPlay(name = name)
        viewModelScope.launch {
            wantToPlayDao.insertWantToPlay(wantToPlay)
            uploadWantToPlay(convertToNetworkWantToPlay(wantToPlay))
        }
    }

    fun updateWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.updateWantToPlay(wantToPlay)
            uploadWantToPlay(convertToNetworkWantToPlay(wantToPlay))
        }
    }

    fun deleteWantToPlay(wantToPlay: WantToPlay) {
        viewModelScope.launch {
            wantToPlayDao.deleteWantToPlay(wantToPlay)
        }
    }

    private fun convertToNetworkWantToPlay(wantToPlay: WantToPlay): NetworkWantToPlay {
        return NetworkWantToPlay(
            name = wantToPlay.name,
        )
    }

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