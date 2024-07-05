package ru.te3ka.boardgamerdiary.model.network_dataclasses

/**
 * Класс, представляющий контакт в формате, используемом для сетевого обмена данными.
 *
 * @property id Идентификатор контакта.
 * @property phone Телефонный номер контакта.
 * @property nickname Псевдоним контакта.
 * @property firstName Имя контакта.
 * @property surname Фамилия контакта.
 */
data class NetworkContact(
    var id: Int = 0,
    var phone: String,
    var nickname: String,
    var firstName: String,
    var surname: String
)