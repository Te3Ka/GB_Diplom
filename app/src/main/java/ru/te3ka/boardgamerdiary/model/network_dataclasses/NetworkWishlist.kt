package ru.te3ka.boardgamerdiary.model.network_dataclasses

/**
 * Класс, представляющий игру или предмет в списке желаемого пользователя, в формате, используемом для сетевого обмена данными.
 *
 * @property id Идентификатор записи о желаемом предмете.
 * @property name Название игры или предмета в списке желаемого.
 */
data class NetworkWishlist(
    val id: Int = 0,
    val name: String
)