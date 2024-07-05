package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс, представляющий список желаемых игр или предметов пользователя.
 *
 * @property id Уникальный идентификатор записи, автоматически генерируемый базой данных.
 * @property name Название игры или предмета, который пользователь хочет добавить в свой список желаемого.
 */
@Entity(tableName = "wishlist")
data class Wishlist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
