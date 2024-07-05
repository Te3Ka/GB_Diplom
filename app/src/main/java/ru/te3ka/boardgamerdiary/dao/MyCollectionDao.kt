package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.MyCollection

/**
 * DAO (Data Access Object) для работы с таблицей коллекций в базе данных.
 */
@Dao
interface MyCollectionDao {
    /**
     * Возвращает все коллекции из таблицы.
     * @return Flow список всех коллекций.
     */
    @Query("SELECT * FROM my_collection")
    fun getAllMyCollection(): Flow<List<MyCollection>>

    /**
     * Вставляет новую коллекцию в таблицу. Если коллекция уже существует, она заменяется.
     * @param myCollection Вставляемая коллекция.
     * @return Долгий идентификатор новой вставленной строки.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyCollection(myCollection: MyCollection) : Long

    /**
     * Обновляет существующую коллекцию в таблице.
     * @param myCollection Обновляемая коллекция.
     */
    @Update
    suspend fun updateMyCollection(myCollection: MyCollection)

    /**
     * Удаляет коллекцию из таблицы.
     * @param myCollection Удаляемая коллекция.
     */
    @Delete
    suspend fun deleteMyCollection(myCollection: MyCollection)
}