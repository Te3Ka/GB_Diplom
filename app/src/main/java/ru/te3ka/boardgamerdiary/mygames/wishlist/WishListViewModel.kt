package ru.te3ka.boardgamerdiary.mygames.wishlist

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
import ru.te3ka.boardgamerdiary.dao.WishlistDao
import ru.te3ka.boardgamerdiary.db.BgdDatabase
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWantToPlay
import ru.te3ka.boardgamerdiary.model.network_dataclasses.NetworkWishlist
import ru.te3ka.boardgamerdiary.service.RetrofitClient

class WishListViewModel(application: Application) : AndroidViewModel(application) {
    private val wishlistDao: WishlistDao = BgdDatabase.getDatabase(application).wishlistDao()
    val allWishlist: Flow<List<Wishlist>> = wishlistDao.getAllWishlist()

    fun addWishlist(name: String) {
        val wishlist = Wishlist(name = name)
        viewModelScope.launch {
            wishlistDao.insertWishlist(wishlist)
            uploadWishlist(convertToNetworkWishlist(wishlist))
        }
    }

    fun updateWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.updateWishlist(wishlist)
            uploadWishlist(convertToNetworkWishlist(wishlist))
        }
    }

    fun deleteWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.deleteWishlist(wishlist)
        }
    }

    private fun convertToNetworkWishlist(wishlist: Wishlist): NetworkWishlist {
        return NetworkWishlist(
            name = wishlist.name,
        )
    }

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