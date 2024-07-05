package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс, представляющий коллекцию настольных игр.
 *
 * @property id Уникальный идентификатор коллекции (автоматически генерируется базой данных).
 * @property name Название коллекции.
 * @property score Оценка коллекции.
 * @property numberOfGames Количество игр в коллекции.
 * @property yearOfPurchase Год приобретения коллекции.
 * @property monthOfPurchase Месяц приобретения коллекции.
 */
@Entity(tableName = "my_collection")
data class MyCollection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val score: String,
    val numberOfGames: String,
    val yearOfPurchase: String,
    val monthOfPurchase: String
)
