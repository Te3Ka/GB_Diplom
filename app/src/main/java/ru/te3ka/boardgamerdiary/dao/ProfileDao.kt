package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.Contact
import ru.te3ka.boardgamerdiary.model.MyCollection
import ru.te3ka.boardgamerdiary.model.Profile
import ru.te3ka.boardgamerdiary.model.WantToPlay
import ru.te3ka.boardgamerdiary.model.Wishlist

/**
 * DAO (Data Access Object) для работы с профилем пользователя в базе данных.
 */
@Dao
interface ProfileDao {
    /**
     * Возвращает профиль пользователя.
     * @return Flow, который содержит профиль пользователя или null, если профиль не найден.
     */
    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Flow<Profile?>

    /**
     * Вставляет профиль в таблицу. Если профиль уже существует, он заменяется.
     * @param profile Вставляемый профиль.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    /**
     * Обновляет существующий профиль в таблице.
     * @param profile Обновляемый профиль.
     */
    @Update
    suspend fun updateProfile(profile: Profile)

    /**
     * Удаляет профиль из таблицы.
     * @param profile Удаляемый профиль.
     */
    @Delete
    suspend fun deleteProfile(profile: Profile)

    /**
     * Возвращает идентификатор контакта по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор контакта или null, если контакт не найден.
     */
    @Query("SELECT contactId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getContactId(contactPhoneNumber: String): Int?

    /**
     * Возвращает идентификатор коллекции по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор коллекции или null, если коллекция не найдена.
     */
    @Query("SELECT myCollectionId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getMyCollectionId(contactPhoneNumber: String): Int?

    /**
     * Возвращает идентификатор списка желаемого по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор списка желаемого или null, если список не найден.
     */
    @Query("SELECT wishlistId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWishListId(contactPhoneNumber: String): Int?

    /**
     * Возвращает идентификатор списка игр "хочу сыграть" по номеру телефона.
     * @param contactPhoneNumber Номер телефона контакта.
     * @return Идентификатор списка игр "хочу сыграть" или null, если список не найден.
     */
    @Query("SELECT wantToPlayId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWantToPlayId(contactPhoneNumber: String): Int?

    /**
     * Вставляет контакт в таблицу.
     * @param contact Вставляемый контакт.
     */
    @Insert
    suspend fun insertContact(contact: Contact)

    /**
     * Вставляет коллекцию в таблицу.
     * @param myCollection Вставляемая коллекция.
     */
    @Insert
    suspend fun insertMyCollection(myCollection: MyCollection)

    /**
     * Вставляет список желаемого в таблицу.
     * @param wishlist Вставляемый список желаемого.
     */
    @Insert
    suspend fun insertWishlist(wishlist: Wishlist)

    /**
     * Вставляет список игр "хочу сыграть" в таблицу.
     * @param wantToPlay Вставляемый список игр "хочу сыграть".
     */
    @Insert
    suspend fun insertWantToPlay(wantToPlay: WantToPlay)
}