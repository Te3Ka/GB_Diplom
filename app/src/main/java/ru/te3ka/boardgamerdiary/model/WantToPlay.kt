package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс, представляющий желаемые настольные игры пользователя.
 *
 * @property id Уникальный идентификатор записи, автоматически генерируемый базой данных.
 * @property name Название желаемой настольной игры.
 */
@Entity(tableName = "want_to_play")
data class WantToPlay(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
