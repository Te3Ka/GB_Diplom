package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.WantToPlay

/**
 * DAO (Data Access Object) для работы со списком игр "хочу сыграть" в базе данных.
 */
@Dao
interface WantToPlayDao {
    /**
     * Возвращает все записи из таблицы want_to_play.
     * @return Flow, который содержит список всех записей want_to_play.
     */
    @Query("SELECT * FROM want_to_play")
    fun getAllWantToPlay(): Flow<List<WantToPlay>>

    /**
     * Вставляет запись в таблицу want_to_play. Если запись уже существует, она заменяется.
     * @param wantToPlay Вставляемая запись.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWantToPlay(wantToPlay: WantToPlay)

    /**
     * Обновляет существующую запись в таблице want_to_play.
     * @param wantToPlay Обновляемая запись.
     */
    @Update
    suspend fun updateWantToPlay(wantToPlay: WantToPlay)

    /**
     * Удаляет запись из таблицы want_to_play.
     * @param wantToPlay Удаляемая запись.
     */
    @Delete
    suspend fun deleteWantToPlay(wantToPlay: WantToPlay)
}