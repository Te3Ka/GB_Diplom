package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.Wishlist

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist")
    fun getAllWishlist(): Flow<List<Wishlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: Wishlist)

    @Update
    suspend fun updateWishlist(wishlist: Wishlist)

    @Delete
    suspend fun deleteWishlist(wishlist: Wishlist)
}