package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.MyCollection

@Dao
interface MyCollectionDao {
    @Query("SELECT * FROM my_collection")
    fun getAllMyCollection(): Flow<List<MyCollection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyCollection(myCollection: MyCollection) : Long

    @Update
    suspend fun updateMyCollection(myCollection: MyCollection)

    @Delete
    suspend fun deleteMyCollection(myCollection: MyCollection)
}