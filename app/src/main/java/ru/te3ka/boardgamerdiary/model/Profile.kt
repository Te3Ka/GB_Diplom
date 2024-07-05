package ru.te3ka.boardgamerdiary.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Класс, представляющий профиль пользователя.
 *
 * @property contactPhone Номер телефона контакта, который является уникальным идентификатором профиля.
 * @property contactId Идентификатор контакта, связанного с профилем (nullable).
 * @property myCollectionId Идентификатор коллекции настольных игр, связанной с профилем (nullable).
 * @property wantToPlayId Идентификатор желаемых игр, связанных с профилем (nullable).
 * @property wishlistId Идентификатор списка желаемого, связанного с профилем (nullable).
 * @property nickname Прозвище пользователя.
 * @property firstName Имя пользователя.
 * @property surname Фамилия пользователя.
 * @property city Город пользователя.
 * @property email Адрес электронной почты пользователя.
 * @property hobbies Хобби пользователя.
 * @property dayOfBirth День рождения пользователя.
 * @property monthOfBirth Месяц рождения пользователя.
 * @property yearOfBirth Год рождения пользователя.
 * @property photoPath Путь к фотографии профиля пользователя.
 */
@Entity(
    foreignKeys = [
        ForeignKey(entity = Contact::class, parentColumns = ["id"], childColumns = ["contactId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = MyCollection::class, parentColumns = ["id"], childColumns = ["myCollectionId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Wishlist::class, parentColumns = ["id"], childColumns = ["wishlistId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = WantToPlay::class, parentColumns = ["id"], childColumns = ["wantToPlayId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Profile(
    @PrimaryKey(autoGenerate = false) val contactPhone: String,
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
