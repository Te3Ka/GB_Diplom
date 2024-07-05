package ru.te3ka.boardgamerdiary.model.network_dataclasses

/**
 * Класс, представляющий профиль пользователя в формате, используемом для сетевого обмена данными.
 *
 * @property contactPhone Номер телефона пользователя.
 * @property contactId Идентификатор контакта пользователя.
 * @property myCollectionId Идентификатор коллекции игр пользователя.
 * @property wantToPlayId Идентификатор списка желаемых игр пользователя.
 * @property wishlistId Идентификатор списка желаемых игр пользователя.
 * @property nickname Псевдоним пользователя.
 * @property firstName Имя пользователя.
 * @property surname Фамилия пользователя.
 * @property city Город проживания пользователя.
 * @property email Адрес электронной почты пользователя.
 * @property hobbies Хобби пользователя.
 * @property dayOfBirth День рождения пользователя.
 * @property monthOfBirth Месяц рождения пользователя.
 * @property yearOfBirth Год рождения пользователя.
 * @property photoPath Путь к фото профиля пользователя.
 */
data class NetworkProfile (
    val contactPhone: String,
    val contactId: Int?,
    val myCollectionId: Int?,
    val wantToPlayId: Int?,
    val wishlistId: Int?,
    val nickname: String,
    val firstName: String,
    val surname: String,
    val city: String,
    val email: String,
    val hobbies: String,
    val dayOfBirth: Int,
    val monthOfBirth: Int,
    val yearOfBirth: Int,
    val photoPath: String
)