package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.WantToPlay

@Dao
interface WantToPlayDao {
    @Query("SELECT * FROM want_to_play")
    fun getAllWantToPlay(): Flow<List<WantToPlay>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWantToPlay(wantToPlay: WantToPlay)

    @Update
    suspend fun updateWantToPlay(wantToPlay: WantToPlay)

    @Delete
    suspend fun deleteWantToPlay(wantToPlay: WantToPlay)

}