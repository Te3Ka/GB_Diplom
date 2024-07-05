package ru.te3ka.boardgamerdiary.repository

import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.dao.ProfileDao
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist

/**
 * Репозиторий для работы с данными профиля.
 * Обеспечивает доступ к операциям вставки, обновления и удаления профилей и связанных данных.
 *
 * @param profileDao DAO для работы с профилями в базе данных.
 */
class ProfileRepository(
    private val profileDao: ProfileDao
) {
    val profile: Flow<Profile?> = profileDao.getProfile()

    /**
     * Вставляет новый профиль в базу данных.
     * @param profile Профиль, который нужно вставить.
     */
    suspend fun insert(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    /**
     * Обновляет существующий профиль в базе данных.
     * @param profile Профиль с обновленными данными.
     */
    suspend fun update(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    /**
     * Удаляет профиль из базы данных.
     * @param profile Профиль, который нужно удалить.
     */
    suspend fun delete(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    /**
     * Получает идентификатор контакта по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор контакта. Возвращает 0, если контакт не найден.
     */
    suspend fun getContactId(contactPhoneNumber: String) : Int {
        return profileDao.getContactId(contactPhoneNumber) ?: 0
    }

    /**
     * Получает идентификатор коллекции по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор коллекции. Возвращает 0, если коллекция не найдена.
     */
    suspend fun getMyCollectionId(contactPhoneNumber: String) : Int {
        return profileDao.getMyCollectionId(contactPhoneNumber) ?: 0
    }

    /**
     * Получает идентификатор списка желаемого по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор списка желаемого. Возвращает 0, если список желаемого не найден.
     */
    suspend fun getWishlistId(contactPhoneNumber: String) : Int {
        return profileDao.getWishListId(contactPhoneNumber) ?: 0
    }

    /**
     * Получает идентификатор списка игр, которые хочется сыграть, по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор списка игр, которые хочется сыграть. Возвращает 0, если список игр не найден.
     */
    suspend fun getWantToPlayId(contactPhoneNumber: String) : Int {
        return profileDao.getWantToPlayId(contactPhoneNumber) ?: 0
    }

    /**
     * Вставляет новый контакт в базу данных.
     * @param contact Контакт, который нужно вставить.
     */
    suspend fun insertContact(contact: Contact) {
        profileDao.insertContact(contact)
    }

    /**
     * Вставляет новую коллекцию в базу данных.
     * @param myCollection Коллекция, которую нужно вставить.
     */
    suspend fun insertMyCollection(myCollection: MyCollection) {
        profileDao.insertMyCollection(myCollection)
    }

    /**
     * Вставляет новый элемент в список желаемого.
     * @param wishlist Элемент списка желаемого, который нужно вставить.
     */
    suspend fun insertWishlist(wishlist: Wishlist) {
        profileDao.insertWishlist(wishlist)
    }

    /**
     * Вставляет новый элемент в список игр, которые хочется сыграть.
     * @param wantToPlay Элемент списка игр, которые хочется сыграть, который нужно вставить.
     */
    suspend fun insertWantToPlay(wantToPlay: WantToPlay) {
        profileDao.insertWantToPlay(wantToPlay)
    }
}