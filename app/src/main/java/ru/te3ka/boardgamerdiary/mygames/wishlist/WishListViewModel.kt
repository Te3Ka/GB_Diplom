package ru.te3ka.boardgamerdiary.mygames.wishlist

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
import ru.te3ka.boardgamerdiary.dao.WishlistDao
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.Wishlist
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWishlist
import ru.te3ka.boardgamerdiary.service.RetrofitClient

/**
 * ViewModel для управления данными списка желаемых игр.
 *
 * Этот ViewModel предоставляет доступ к данным списка желаемых игр и управляет их
 * синхронизацией между локальной базой данных и сетевым сервером.
 *
 * @param application Приложение, связанное с ViewModel.
 */
class WishListViewModel(application: Application) : AndroidViewModel(application) {
    private val wishlistDao: WishlistDao = BgdDatabase.getDatabase(application).wishlistDao()
    val allWishlist: Flow<List<Wishlist>> = wishlistDao.getAllWishlist()

    /**
     * Добавляет новую желаемую игру в базу данных и синхронизирует ее с сервером.
     *
     * @param name Название желаемой игры.
     */
    fun addWishlist(name: String) {
        val wishlist = Wishlist(name = name)
        viewModelScope.launch {
            wishlistDao.insertWishlist(wishlist)
            uploadWishlist(convertToNetworkWishlist(wishlist))
        }
    }

    /**
     * Обновляет существующую желаемую игру в базе данных и синхронизирует изменения с сервером.
     *
     * @param wishlist Обновленный объект желаемой игры.
     */
    fun updateWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.updateWishlist(wishlist)
            uploadWishlist(convertToNetworkWishlist(wishlist))
        }
    }

    /**
     * Удаляет желаемую игру из базы данных.
     *
     * @param wishlist Объект желаемой игры для удаления.
     */
    fun deleteWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.deleteWishlist(wishlist)
        }
    }

    /**
     * Преобразует объект [Wishlist] в объект [NetworkWishlist] для отправки на сервер.
     *
     * @param wishlist Объект желаемой игры, который нужно преобразовать.
     * @return Преобразованный объект [NetworkWishlist].
     */
    private fun convertToNetworkWishlist(wishlist: Wishlist): NetworkWishlist {
        return NetworkWishlist(
            name = wishlist.name,
        )
    }

    /**
     * Отправляет объект [NetworkWishlist] на сервер для синхронизации данных.
     *
     * @param networkWishlist Объект желаемой игры в формате, подходящем для отправки на сервер.
     */
    private fun uploadWishlist(networkWishlist: NetworkWishlist) {
        RetrofitClient.apiService.uploadWishlist(networkWishlist).enqueue(object :
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