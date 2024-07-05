package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс, представляющий контакт в базе данных.
 *
 * @property id Уникальный идентификатор контакта, автоматически генерируемый при добавлении нового контакта.
 * @property phone Номер телефона контакта.
 * @property nickname Псевдоним контакта.
 * @property firstName Имя контакта.
 * @property surname Фамилия контакта.
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var phone: String,
    var nickname: String,
    var firstName: String,
    var surname: String
)
