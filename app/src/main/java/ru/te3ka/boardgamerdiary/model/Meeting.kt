package ru.te3ka.boardgamerdiary.model

/**
 * Класс, представляющий встречу.
 *
 * @property date Дата встречи в формате строки (например, "2024-07-05").
 * @property location Место проведения встречи.
 * @property boardgames Список названий настольных игр, которые будут использоваться на встрече.
 * @property contacts Список номеров телефонов контактов, приглашенных на встречу.
 */
data class Meeting(
    val date: String,
    val location: String,
    val boardgames: List<String>,
    val contacts: List<String>
)