package ru.te3ka.boardgamerdiary.mygames.wishlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.te3ka.boardgamerdiary.dao.WishlistDao
import ru.te3ka.boardgamerdiary.db.WishlistDatabase
import ru.te3ka.boardgamerdiary.model.Wishlist

class WishListViewModel(application: Application) : AndroidViewModel(application) {
    private val wishlistDao: WishlistDao = WishlistDatabase.getWishlistDatabase(application).wishlistDao()
    val allWishlist: Flow<List<Wishlist>> = wishlistDao.getAllWishlist()

    fun addWishlist(name: String) {
        val wishlist = Wishlist(name = name)
        viewModelScope.launch {
            wishlistDao.insertWishlist(wishlist)
        }
    }

    fun updateWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.updateWishlist(wishlist)
        }
    }

    fun deleteWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            wishlistDao.deleteWishlist(wishlist)
        }
    }
}