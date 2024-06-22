package ru.te3ka.boardgamerdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.te3ka.boardgamerdiary.model.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT contactId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getContactId(contactPhoneNumber: String): Int

    @Query("SELECT myCollectionId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getMyCollectionId(contactPhoneNumber: String): Int

    @Query("SELECT wishlistId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWishListId(contactPhoneNumber: String): Int

    @Query("SELECT wantToPlayId FROM profile WHERE contactPhone =:contactPhoneNumber")
    suspend fun getWantToPlayId(contactPhoneNumber: String): Int
}