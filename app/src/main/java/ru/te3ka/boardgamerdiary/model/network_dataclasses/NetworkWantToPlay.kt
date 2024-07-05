package ru.te3ka.boardgamerdiary.model.network_dataclasses

/**
 * Класс, представляющий игру, которую пользователь хочет сыграть, в формате, используемом для сетевого обмена данными.
 *
 * @property id Идентификатор записи о игре.
 * @property name Название игры, которую пользователь хочет сыграть.
 */
data class NetworkWantToPlay(
    val id: Int = 0,
    val name: String
)