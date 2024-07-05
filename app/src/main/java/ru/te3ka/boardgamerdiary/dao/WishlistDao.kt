package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.Wishlist

/**
 * DAO (Data Access Object) для работы со списком желаемых игр в базе данных.
 */
@Dao
interface WishlistDao {
    /**
     * Возвращает все записи из таблицы wishlist.
     * @return Flow, который содержит список всех записей wishlist.
     */
    @Query("SELECT * FROM wishlist")
    fun getAllWishlist(): Flow<List<Wishlist>>

    /**
     * Вставляет запись в таблицу wishlist. Если запись уже существует, она заменяется.
     * @param wishlist Вставляемая запись.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: Wishlist)

    /**
     * Обновляет существующую запись в таблице wishlist.
     * @param wishlist Обновляемая запись.
     */
    @Update
    suspend fun updateWishlist(wishlist: Wishlist)

    /**
     * Удаляет запись из таблицы wishlist.
     * @param wishlist Удаляемая запись.
     */
    @Delete
    suspend fun deleteWishlist(wishlist: Wishlist)
}