package ru.te3ka.boardgamerdiary.dao

import androidx.lifecycle.LiveData
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

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Flow<Profile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT contactId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getContactId(contactPhoneNumber: String): Int?

    @Query("SELECT myCollectionId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getMyCollectionId(contactPhoneNumber: String): Int?

    @Query("SELECT wishlistId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWishListId(contactPhoneNumber: String): Int?

    @Query("SELECT wantToPlayId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWantToPlayId(contactPhoneNumber: String): Int?

    @Insert
    suspend fun insertContact(contact: Contact)

    @Insert
    suspend fun insertMyCollection(myCollection: MyCollection)

    @Insert
    suspend fun insertWishlist(wishlist: Wishlist)

    @Insert
    suspend fun insertWantToPlay(wantToPlay: WantToPlay)
}