package ru.te3ka.boardgamerdiary.model.network_dataclasses

/**
 * Класс, представляющий коллекцию игр в формате, используемом для сетевого обмена данными.
 *
 * @property id Идентификатор коллекции игр.
 * @property name Название коллекции игр.
 * @property score Рейтинг коллекции игр.
 * @property numberOfGames Количество игр в коллекции.
 * @property yearOfPurchase Год приобретения коллекции игр.
 * @property monthOfPurchase Месяц приобретения коллекции игр.
 */
data class NetworkMyCollection(
    val id: Int = 0,
    val name: String,
    val score: String,
    val numberOfGames: String,
    val yearOfPurchase: String,
    val monthOfPurchase: String
)